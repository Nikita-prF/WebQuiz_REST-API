package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * <h2>The Class defining the model of the database entity "User".</h2>
 *
 * The objects of the class are a user model.
 *
 * The entity has 5 attributes:
 *  * User Id,
 *  * Email as login,
 *  * password encoded by <b>BCrypt</b>,
 *  * Two Collections that store the user created quizzes
 *      and quizzes that the user has successfully solved.
 *
 * The <b>"UserDetails"</b> interface implements the ability to authentication the model
 * of the "User" class.
 *
 */
@Entity
public class User implements UserDetails {

    /**
     * User id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * Email as login
     */
    @Pattern(regexp=".+@.+\\..+", message = "Invalid format")
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid format")
    @Column(name="email")
    private String email;


    /**
     * password encoded by BCrypt
     */
    @NotNull(message = "Password cannot be empty")
    @Size(min = 5, message = "Password must contain a minimum of 5 symbols.")
    @Column(name = "password")
    private String password;

    /**
     * The Collection of user created quizzes related to the {@link engine.entity.Quiz}
     * entity.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    /**
     * The Collection of user successfully solved quizzes
     * related to the {@link engine.entity.CompletedQuiz} entity.
     *
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CompletedQuiz> completedQuizs = new ArrayList<>();


    /* Getters and Setters */

    public Long getId() {
        return id;
    }
    public void setId(Long id) { this.id = id; }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<CompletedQuiz> getCompletedQuizs() { return completedQuizs; }
    public void setCompletedQuizs(List<CompletedQuiz> completedQuizs) { this.completedQuizs = completedQuizs; }
    public List<Quiz> getQuizzes() {
        return quizzes;
    }
    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }


    /* Interface method overriding */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
