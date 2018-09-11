package br.com.marinete.api.services.impl;

import br.com.marinete.api.entities.User;
import br.com.marinete.api.repositories.UserRepository;
import br.com.marinete.api.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByCpf(String cpf) {
        log.info("Finding user by cpf: {}", cpf);
        return Optional.ofNullable(this.userRepository.findByCpf(cpf));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return Optional.ofNullable(this.userRepository.findByEmail(email));
    }

    @Override
    public User persist(User user) {
        log.info("Saving user in database {}", user);
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return this.userRepository.findById(id);
    }
}
