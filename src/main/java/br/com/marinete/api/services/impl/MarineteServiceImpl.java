package br.com.marinete.api.services.impl;


import br.com.marinete.api.entities.Marinete;
import br.com.marinete.api.repositories.MarineteRepository;
import br.com.marinete.api.services.MarineteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MarineteServiceImpl implements MarineteService{

    private  static final Logger log = LoggerFactory.getLogger(MarineteServiceImpl.class);

    @Autowired
    private MarineteRepository marineteRepository;

    @Override
    public Optional<Marinete> findByCtps(Integer ctps){
        log.info("Finding one marinete with CTPS {}", ctps);
        return Optional.ofNullable(this.marineteRepository.findByCtps(ctps));
    }

    @Override
    public Marinete persist(Marinete marinete) {
        log.info("Saving Marinete: {}", marinete);
        return this.marineteRepository.save(marinete);
    }

    @Override
    public Optional<Marinete> findMarineteById(Long id) {
        log.info("Finding Marinete by id {}", id);
        return Optional.ofNullable(this.marineteRepository.findById(id));
    }
}
