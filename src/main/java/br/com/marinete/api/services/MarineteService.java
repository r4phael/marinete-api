package br.com.marinete.api.services;

import br.com.marinete.api.entities.Marinete;

import java.util.Optional;

public interface MarineteService {

    /**
     * Return the Marinete from CTPS
     *
     * @param ctps
     * @return Optional<Marinete>
     */

    Optional<Marinete> findByCtps(Integer ctps);

    /**
     * Register one Marinete in database
     *
     * @param marinete
     * @return Marinete
     */

    Marinete persist(Marinete marinete);

    /**
     * Find a Marinete by ID
     *
     * @param id
     * @return Marinete
     */
    Optional<Marinete> findMarineteById(Long id);


}
