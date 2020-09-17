package engine.controller;

import engine.repository.UserRepository;
import engine.entity.User;
import engine.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * The base Class that controls and handles user requests for registration.
 */
@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;


    /**
     * User POST request handling method for registration
     * @param newUser JSON body with user data for registration,
     *               recording into "User" object for validation and later saving into database
     * @return Optional response object
     */
    @PostMapping(value = "/register", consumes = "application/json")
    @ResponseBody
    public Object register(@Valid @RequestBody User newUser) {
        Optional<User> user = userDetailsService.findByEmail(newUser.getEmail());

        if (user.isPresent()) {
            return new ResponseEntity<>("User already exist",HttpStatus.BAD_REQUEST);
        } else {
            userDetailsService.save(newUser);
            return new ResponseEntity<>("You are registered!" ,HttpStatus.OK);
        }
    }
}
