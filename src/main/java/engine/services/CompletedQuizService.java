package engine.services;

import engine.entity.CompletedQuiz;
import engine.entity.User;
import engine.repository.PagingSortingComplQuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * The service class that implements the method of returning all solved
 * quizzes of the current user with sorting and paging,
 * which is possible by using our special interface
 * {@link engine.repository.PagingSortingComplQuizRepo}
 *
 */
@Service
public class CompletedQuizService {
    @Autowired
    private PagingSortingComplQuizRepo repository;


    /**
     * The method takes page number, page size (number of records per page),
     * sorting method and user object as arguments
     * and returns "Page" object with all found results
     * sorted and divided into pages according to these arguments.
     *
     * @param pageNo Page number
     * @param pageSize Page size
     * @param sortBy Sorting method
     * @param user User object
     * @return "Page" object with all found results
     */
    public Page<CompletedQuiz> getAllQuizzes
            (Integer pageNo,
            Integer pageSize,
            String sortBy,
            User user) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return repository.findAll(paging, user);
    }
}
