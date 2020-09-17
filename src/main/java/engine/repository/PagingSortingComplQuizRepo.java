package engine.repository;

import engine.entity.CompletedQuiz;
import engine.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Management interface of the entity object {@link engine.entity.CompletedQuiz} in the database
 */
@Repository
public interface PagingSortingComplQuizRepo
        extends PagingAndSortingRepository<CompletedQuiz, Long> {


    /**
     * The method executes an SQL query to a database and returns
     * sorted and paging object "Page" with result.
     *
     *
     * @param pageable Page sorting and paging options
     * @param user Object of User
     * @return "Page" object as result of SQL query
     */
    @Query("SELECT a FROM CompletedQuiz a WHERE a.user = :user")
    Page<CompletedQuiz> findAll(Pageable pageable, @Param("user") User user);

}
