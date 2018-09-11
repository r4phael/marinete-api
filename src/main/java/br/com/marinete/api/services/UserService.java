package br.com.marinete.api.services;

import br.com.marinete.api.entities.User;

import java.util.Optional;

public interface UserService {


    /**
     * Find one user by Cpf.
     *
     * @param cpf
     * @return User
     */
    Optional<User> findByCpf(String cpf);

    /**
     * Find one user by email.
     *
     * @param email
     * @return User
     */
    Optional<User> findByEmail(String email);

    /**
     * Persist a User in database
     *
     * @param user
     * @return User
     */

    User persist(User user);

    /**
     * Find a user by ID
     *
     * @param id
     * @return User
     */
    Optional<User> findUserById(Long id);
}
