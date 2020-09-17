package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * <h2>The Class defining the model of the database entity "CompletedQuiz".</h2>
 *
 * The objects of the class store information about
 * each successfully solved quiz by a certain user
 *
 * The entity has 4 attributes:
 *  * Primary log id,
 *  * Quiz id,
 *  * The object of the user who successfully solved the quiz,
 *  * Timestamp of solving
 */
@Entity
public class CompletedQuiz {


    /**
     * Primary log id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long primaryId;

    /**
     * Quiz id
     */
    @Column
    private long id;


    /**
     * The object of the user who successfully solved the quiz
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user")
    private User user;

    /**
     * Timestamp of solving
     */
    @NotNull
    @Column
    private Date completedAt;

    /**
     * Builder that sets Timestamp and quiz id after creating a log object.
     *
     * @param date Timestamp
     * @param quizId Quiz id
     */
    public CompletedQuiz(Date date, long quizId) {
        this.completedAt = date;
        this.id = quizId;
    }


    /* Setters and Getters */

    @JsonIgnore
    public Long getPrimaryId() {
        return primaryId;
    }

    @JsonIgnore
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }
}
