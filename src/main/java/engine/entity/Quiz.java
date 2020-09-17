package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <h2>The Class defining the model of the database entity "Quiz".</h2>
 *
 *   The objects of the class is a model of quizzes created by users.
 *
 *   The entity has 6 attributes:
 *   * Quiz Id,
 *   * Title of quiz,
 *   * Quiz text,
 *   * Array of strings as answer choices,
 *   * Integer array of answers,
 *   * The model of the user who created this quiz.
 *
 */
@Entity
public class Quiz {

    /**
     * Quiz id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the quiz
     */
    @Column(name = "title")
    @NotNull(message = "Title cannot be empty")
    private String title;

    /**
     * Quiz text
     */
    @Column(name = "text")
    @NotNull(message = "Text cannot be empty")
    private String text;

    /**
     * Array of strings as answer choices
     * By the condition, there can be at least two answer choices
     */
    @Column(name = "options")
    @NotNull(message = "Should be the answer options here.")
    @Size(min = 2, message = "There must be at least two answer choices")
    private String[] options;

    /**
     * Integer array that stores index of correct answers
     * from an array of answers. It may be empty if there are
     * no correct answers.
     *
     * Any user is not allowed to access correct answers to the quiz.
     */
    @Column(name = "answer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    /**
     * The object of the user who created the quiz.
     * This field is related to the 'User' entity.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    /* Setters and Getters */

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getText() {
        return text;
    }
    public String[] getOptions() {
        return options;
    }

    @JsonIgnore
    public int[] getAnswer() {
        return answer;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonProperty
    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setOptions(String[] options) {
        this.options = options;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
