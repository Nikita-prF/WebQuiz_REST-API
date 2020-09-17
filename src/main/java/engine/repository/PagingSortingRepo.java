package engine.repository;

import engine.entity.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 *  Management interface of the entity object {@link engine.entity.Quiz} in the database
 */
@Repository
public interface PagingSortingRepo extends PagingAndSortingRepository<Quiz, Long> {
    ArrayList<Quiz> findAll();
}

