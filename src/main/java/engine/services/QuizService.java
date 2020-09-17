package engine.services;

import engine.entity.Quiz;
import engine.entity.User;
import engine.repository.PagingSortingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The service class that implements the method of returning
 * all available quizzes, which is possible by using our special interface
 * {@link engine.repository.PagingSortingRepo}
 */
@Service
public class QuizService
{
    @Autowired
    PagingSortingRepo repository;

    /**
     * This method is a copy of the
     * {@link engine.services.CompletedQuizService#getAllQuizzes(Integer, Integer, String, User)}
     * method except for the unavailable search by the current user.
     *
     * @param pageNo Page number
     * @param pageSize Page size
     * @param sortBy Sorting method
     * @return "Page" object with all found results
     */
    public Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return repository.findAll(paging);

    }
}
