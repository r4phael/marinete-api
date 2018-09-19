package br.com.marinete.api.controllers;

import br.com.marinete.api.dtos.MarineteDto;
import br.com.marinete.api.entities.Marinete;
import br.com.marinete.api.response.Response;
import br.com.marinete.api.services.MarineteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/marinete")
@CrossOrigin(origins = "*")
public class MarineteController {

    public static final Logger log = LoggerFactory.getLogger(MarineteController.class);

    @Autowired
    private MarineteService marineteService;

    public MarineteController() {
    }

    /**
     * Registration of a Marinete in the system.
     *
     * @param marineteDto
     * @param result
     * @return ResponseEntity<MarineteDto>
     */

    @PostMapping
    public ResponseEntity<Response<MarineteDto>> save(@Valid @RequestBody MarineteDto marineteDto, BindingResult result) throws Exception {

        log.info("Saving Marinete: {}", marineteDto.toString());
        Response<MarineteDto> response = new Response<MarineteDto>();

        validateMarineteData(marineteDto, result);
        Marinete marinete = this.convertDtoToMarinete(marineteDto);

        if (result.hasErrors()){
            log.error("Error in validation of Marinete data: {}", result.getAllErrors());
            result.getAllErrors().forEach(objectError -> response.getErrors().add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.marineteService.persist(marinete);
        response.setData(this.convertMarineteToDto(marinete));

        return ResponseEntity.ok(response);

    }

    /**
     * Return a Marinete in the system by ctps.
     *
     * @param ctps
     * @return ResponseEntity<Response<MarineteDto>>
     */

    @GetMapping(value = "/ctps/{ctps}")
    public ResponseEntity<Response<MarineteDto>> findByCtps(@PathVariable("ctps") Integer ctps){
        log.info("Finding Marinete by CTPS: {}", ctps);
        Response<MarineteDto> response = new Response<>();
        Optional<Marinete> marinete = marineteService.findByCtps(ctps);

        if (!marinete.isPresent()){
            log.info("Marinete not found by CTPS:  ",ctps);
            response.getErrors().add("Marinete not found by CTPS:  "+ ctps);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertMarineteToDto(marinete.get()));
        return ResponseEntity.ok(response);

    }

    /**
     * Convert a Marinete to a DTO object
     *
     * @param marinete
     * @return MarineteDto
     */

    private MarineteDto convertMarineteToDto(Marinete marinete) {

        MarineteDto marineteDto = new MarineteDto();
        marineteDto.setId(marinete.getId());
        marineteDto.setCtps(marinete.getCtps());
        marineteDto.setName(marinete.getName());
        marineteDto.setEmail(marinete.getEmail());
        marineteDto.setEvaluation(marinete.getEvaluation());

        return marineteDto;
    }

    /**
     * Convert one DTO object oto a Marinete.
     * @param marineteDto
     * @return
     */

    private Marinete convertDtoToMarinete(MarineteDto marineteDto) {
        Marinete marinete = new Marinete();
        marinete.setCtps(marineteDto.getCtps());
        marinete.setName(marineteDto.getName());
        marinete.setEmail(marineteDto.getEmail());
        marinete.setEvaluation(marineteDto.getEvaluation());

        return marinete;

    }

    /**
     * Verify if the Marinete already exists in the database.
     * @param marineteDto
     * @return result
     */
    private void validateMarineteData(MarineteDto marineteDto, BindingResult result){
        this.marineteService.findByCtps(marineteDto.getCtps()).
                ifPresent(marinete -> result.addError(new ObjectError("empresa", "Marinette already existing")));

    }


}
