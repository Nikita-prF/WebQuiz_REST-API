package engine.services;

import engine.repository.UserRepository;
import engine.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <h2>Interface implementing user authentication service</h2>
 *
 * The interface implements two main functions
 * - user searching in the system and registration of new users.
 *
 * In this case, "UserDetailsService" is the core interface which loads user-specific data.
 * It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider.
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Implementation of PasswordEncoder that uses the BCrypt strong hashing function.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl() {
    }


    /**
     * A method that performs user search and authentication
     *
     * @param username Name or in this case the user's email
     * @return the object of the found user if it exists in the database.
     * @throws UsernameNotFoundException Exception when there is no user in the database
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }


    /**
     * The method registers a new user, saving it in a database.
     * @param newUser Object of a new user
     */
    public void save(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
