package br.com.marinete.api.repositories;

import br.com.marinete.api.entities.Marinete;
import br.com.marinete.api.entities.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import java.util.List;

@Transactional(readOnly = true)
@NamedQueries({
        @NamedQuery(name = "ServiceRepository.findByMarineteId",
        query = "SELECT serv FROM Service serv WHERE serv.marinete_id = :marineteId") })

public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByMarineteId(@Param("marineteId") Long marineteId);

    Page<Service> findByMarineteId(@Param("marineteId") Long marineteId, Pageable pageable);




}
