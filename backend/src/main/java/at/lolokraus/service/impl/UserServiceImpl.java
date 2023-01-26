package at.lolokraus.service.impl;

import at.lolokraus.model.Authority;
import at.lolokraus.model.User;
import at.lolokraus.model.UserRequest;
import at.lolokraus.model.UserRoleName;
import at.lolokraus.repository.UserRepository;
import at.lolokraus.service.AuthorityService;
import at.lolokraus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthorityService authService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorityService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    //TODO
    @PreAuthorize("hasRole('ADMIN')")
    public void resetCredentials() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setPassword(getBCryptPasswordEncoder().encode("123"));
            userRepository.save(user);
        }
    }

    @Override
    // @PreAuthorize("hasRole('USER')")
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User findById(Long id) throws AccessDeniedException {
        return userRepository.getOne(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }

    @Override
    public User save(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(getBCryptPasswordEncoder().encode(userRequest.getPassword()));
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        List<Authority> auth = authService.findByName(UserRoleName.ROLE_USER);
        user.setAuthorities(auth);
        return userRepository.save(user);
    }



    private BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
