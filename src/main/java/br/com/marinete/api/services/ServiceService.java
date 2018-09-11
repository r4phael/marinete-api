package br.com.marinete.api.services;

import br.com.marinete.api.entities.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface ServiceService {

    /**
     * Return one pagined list of services by Marinete.
     *
     * @param marineteId
     * @param pageRequest
     * @return Page<Service>
     */
    Page<Service> findByMarineteId(long marineteId, PageRequest pageRequest);

    /**
     * Return one service by Id.
     *
     * @param id
     * @return Optional<Service>
     */
    Optional<Service> findById(long id);

    /**
     * Save one Service in database.
     *
     * @param service
     * @return Service
     */
    Service persist(Service service);

    /**
     * Delete one service in database.
     *
     * @param id
     */
    void removeById(long id);





}
