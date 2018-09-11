package br.com.marinete.api.services.impl;

import br.com.marinete.api.entities.Service;
import br.com.marinete.api.repositories.ServiceRepository;
import br.com.marinete.api.services.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private static final Logger log = LoggerFactory.getLogger(ServiceServiceImpl.class);

    @Autowired
    private ServiceRepository serviceRepository;


    @Override
    public Page<Service> findByMarineteId(long marineteId, PageRequest pageRequest) {
        log.info("Finding Services by Marinete ID: {}", marineteId);
        return this.serviceRepository.findByMarineteId(marineteId, pageRequest);
    }

    @Override
    public Optional<Service> findById(long id) {
        log.info("Finding Services by ID: {}", id);
        return this.serviceRepository.findById(id);
    }

    @Override
    public Service persist(Service service) {
        log.info("Saving Service in database: {}", service);
        return this.serviceRepository.save(service);
    }

    @Override
    public void removeById(long id) {
        log.info("Removing Service by ID: {}", id);
        this.serviceRepository.deleteById(id);

    }
}
