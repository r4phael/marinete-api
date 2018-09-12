package br.com.marinete.api.repositories;

import br.com.marinete.api.entities.Marinete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface MarineteRepository extends JpaRepository<Marinete, Integer> {

    @Transactional(readOnly =true)
    Marinete findByCtps(Integer ctps);

    @Transactional(readOnly = true)
    Marinete findById(Long id);

}
