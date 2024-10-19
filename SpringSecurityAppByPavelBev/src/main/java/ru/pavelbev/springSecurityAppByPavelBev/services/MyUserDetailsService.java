package ru.pavelbev.springSecurityAppByPavelBev.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pavelbev.springSecurityAppByPavelBev.configs.CustomUserDetails;
import ru.pavelbev.springSecurityAppByPavelBev.models.User;
import ru.pavelbev.springSecurityAppByPavelBev.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByName(username);
        return user.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException(id + " not found"));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }
    public User saveUserRest(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return user;
    }

    public void deleteUserById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public void updateUser(User user) {
        if (repository.existsById(user.getId())) {
            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
        }
    }

    public Optional<User> findByName(String name) {
        return repository.findByName(name);
    }
}
