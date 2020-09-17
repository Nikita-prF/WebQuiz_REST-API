package engine.controller;

import engine.OptionAnswer;

import engine.entity.CompletedQuiz;
import engine.repository.PagingSortingComplQuizRepo;
import engine.repository.PagingSortingRepo;
import engine.UserAnswer;
import engine.entity.Quiz;
import engine.entity.User;
import engine.repository.UserRepository;
import engine.services.CompletedQuizService;
import engine.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.*;


/**
 * The base Class that controls and handles user requests for quizzes.
 *
 */
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    User curUser;


    @Autowired
    private PagingSortingRepo quizRepo;

    @Autowired
    private PagingSortingComplQuizRepo complQuizRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    QuizService service;

    @Autowired
    CompletedQuizService complQuizService;


    /* Enter customized exception handling */

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found such quiz")
    public static class QuizNotFoundException extends IllegalArgumentException {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid creating")
    public static class InvalidCreatingException extends MethodArgumentNotValidException {

        public InvalidCreatingException(MethodParameter parameter, BindingResult bindingResult) {
            super(parameter, bindingResult);
        }
    }


    /**
     * Quiz creation method
     *
     *  The new object of "Quiz" entity is assigned the current object of User,
     *  text, title, answer choices and correct answers user sends in JSON body by POST request
     *
     * @param quiz JSON request body, which will be a new Quiz object in case of successful validation
     * @return The "Quiz" object recorded in the database
     * @throws InvalidCreatingException Handling of non-valid JSON body input processing.
     */
    @PostMapping(consumes = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) throws InvalidCreatingException {
        quiz.setUser((User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return quizRepo.save(quiz);
    }

    /**
     * Method of handling a request to search for a specific quiz by id
     *
     * @param id Quiz id
     * @return The required quiz
     */
    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable("id") Long id) {

        if (quizRepo.findById(id).isPresent()) {
            return quizRepo.findById(id).get();
        } else {
            throw new QuizNotFoundException();
        }
    }


    /**
     * Method of handling a GET request to search for all available quizzes
     * The query result sorted by id
     *
     *
     * @param pageNo Page number
     * @param pageSize Page size
     * @param sortBy Sorting method
     * @return Sorted and paging object "Page" with result of search
     */
    @GetMapping
    public Page<Quiz> getAllQuiz(
            @RequestParam(name = "page", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortedby", defaultValue = "id") String sortBy)
    {
        return service.getAllQuizzes(pageNo, pageSize, sortBy);
    }


    /**
     * Method of handling a POST request for a certain quiz solving
     *
     * @param id Quiz id
     * @param ans JSON body with an array of answer indexes from the user
     * @return The result of checking the answers and the body of {@link OptionAnswer}
     */
    @PostMapping("/{id}/solve")
    public OptionAnswer postAnswer(@PathVariable("id") Long id, @RequestBody UserAnswer ans) {
        OptionAnswer answer;
        CompletedQuiz completedQuiz;
        curUser = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());


        /* Check for existing quiz by id */
        if (quizRepo.findById(id).isPresent()) {
            Quiz quiz = quizRepo.findById(id).get();

            /* Handling the case of the non-existence
             of correct answers of the quiz and the correct user solving (null != [])
             */
            if (quiz.getAnswer() == null && ans.getAnswer().length == 0) {
                ans.setAnswer(null);
            }
            if (Arrays.equals(ans.getAnswer(), quiz.getAnswer())) {

                answer = new OptionAnswer(true);

                /* Entering data about the quiz and Timestamp into a new object
                 of the resolved quiz and saving it in the database.
                 */
                completedQuiz = new CompletedQuiz(new Date(), id); //
                completedQuiz.setUser(curUser);

                complQuizRepo.save(completedQuiz);

            } else {
                answer = new OptionAnswer(false);
            }
            curUser = null;
            return answer;
        } else {
            throw new QuizNotFoundException();
        }
    }

    /**
     * Method of handling a DELETE request to delete a certain quiz
     *
     * The method verifies that the current user has access to delete a certain quiz.
     * Only the quiz creator may delete our quiz from the database!
     *
     * @param quizId Quiz id
     * @return Response body with optional answer
     */
    @DeleteMapping("/{id}")
    public Object deleteQuizById(@PathVariable("id") Long quizId) {


        Quiz quiz = quizRepo.findById(quizId).orElseThrow(QuizNotFoundException::new);
        curUser = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (!quiz.getUser().getEmail().equals(curUser.getEmail())) {
            return new ResponseEntity<>("Not allowed", HttpStatus.FORBIDDEN);
        }
        curUser = null;
        quizRepo.deleteById(quizId);
        return new ResponseEntity<>("Quiz deleted" ,HttpStatus.NO_CONTENT);

    }

    /**
     * Method of handling a search GET request for all quizzes solved by the current user.
     * The query result is sorted by Timestamp.
     *
     * @param pageNo Page number
     * @param pageSize Page size
     * @param sortBy Sorting method
     * @return Sorted and paging object "Page" with result of search
     */
    @GetMapping("/completed")
    public Page<CompletedQuiz> getCompleted(
            @RequestParam(name = "page", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortedby", defaultValue = "completedAt") String sortBy) {
        curUser = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return complQuizService.getAllQuizzes(pageNo, pageSize, sortBy, curUser);
    }
}