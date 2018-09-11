package br.com.marinete.api.repositories;

import br.com.marinete.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByCpf(String cpf);

    User findByEmail(String cpf);

    User findByEmailOrCpf(String cpf, String email);
}
