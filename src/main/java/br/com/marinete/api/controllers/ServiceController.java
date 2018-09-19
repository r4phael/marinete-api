package br.com.marinete.api.controllers;


import br.com.marinete.api.dtos.ServiceDto;
import br.com.marinete.api.entities.Marinete;
import br.com.marinete.api.entities.Service;
import br.com.marinete.api.entities.User;
import br.com.marinete.api.enums.StatusEnum;
import br.com.marinete.api.response.Response;
import br.com.marinete.api.services.MarineteService;
import br.com.marinete.api.services.ServiceService;
import br.com.marinete.api.services.UserService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    public static final Logger log = LoggerFactory.getLogger(ServiceController.class);
    public final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ServiceService serviceServ;

    @Autowired
    private MarineteService marineteService;

    @Autowired
    private UserService userService;

    @Value("{$pagination.amount_per_page}")
    private String amountPerPage;

    public ServiceController() {
    }



    /**
     * Return the list of services by a Marinete
     *
     * @param marineteId
     * @return ResponseEntity<Response<ServiceDto>>
     */
    @GetMapping(value = "/marinete/{marineteId}")
    public ResponseEntity<Response<Page<ServiceDto>>> listByMarineteId (
            @PathVariable("marineteId") Long marineteId,
            @RequestParam(value = "pag", defaultValue = "0'") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir){

        log.info("Finding services by id of Marinete: {}, page: {}", marineteId, pag);
        Response<Page<ServiceDto>> response = new  Response<Page<ServiceDto>>();

        PageRequest pageRequest = PageRequest.of(pag, Integer.parseInt(this.amountPerPage), Sort.Direction.valueOf(dir), ord);
        Page<Service> services = this.serviceServ.findByMarineteId(marineteId, pageRequest);
        Page<ServiceDto> serviceDto = services.map(service -> this.convertServiceDto(service));

        response.setData(serviceDto);
        return ResponseEntity.ok(response);

    }


    /**
     * Return a service by Id
     *
     * @param id
     * @return ResponseEntity<Response<ServiceDto>>
     */

    @GetMapping(value = "{/id}")
    public ResponseEntity<Response<ServiceDto>> listById (@PathVariable("id") Long id){
        log.info("Fidingin service by Id: {}", id);
        Response<ServiceDto> response = new Response<>();
        Optional<Service> service = this.serviceServ.findById(id);

        if (!service.isPresent()){
            log.info("Service not found by id: {}", id);
            response.getErrors().add("Service not found by id:" + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertServiceDto(service.get()));
        return ResponseEntity.ok(response);

    }

    /**
     * Add the service in database
     *
     * @param serviceDto
     * @param result
     * @return ResponseEntity<Response<ServiceDto>>
     * @throws java.text.ParseException
     */

    @PostMapping
    public ResponseEntity<Response<ServiceDto>> add (@Valid @RequestBody ServiceDto serviceDto, BindingResult result) throws ParseException {

        log.info("Adding service: {}", serviceDto.toString());
        Response<ServiceDto> response = new Response<>();
        validateMarinete(serviceDto, result);
        validateUser(serviceDto, result);
        Service service = this.convertDtoToService(serviceDto, result);

        if (result.hasErrors()){
            log.error("Error validating service: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);

        }

        service = this.serviceServ.persist(service);
        response.setData(this.convertServiceDto(service));
        return ResponseEntity.ok(response);
    }



    /**
     * Update the service in database
     *
     * @param id
     * @param serviceDto
     * @return ResponseEntity<Response<Service>>
     * @throws java.text.ParseException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<ServiceDto>> update (@PathVariable("id") Long id,
                                                        @Valid @RequestBody ServiceDto serviceDto, BindingResult result) throws ParseException{

        log.info("Updating Service: {}", serviceDto.toString());
        Response<ServiceDto> response = new Response<ServiceDto>();
        validateUser(serviceDto, result);
        validateMarinete(serviceDto,result);
        serviceDto.setId(Optional.of(id));
        Service service = this.convertDtoToService(serviceDto, result);

        if (result.hasErrors()) {
            log.error("Error validating Service: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        service = this.serviceServ.persist(service);
        response.setData(this.convertServiceDto(service));
        return ResponseEntity.ok(response);

    }

    /**
     * Delete the service in database
     *
     * @param id
     * @return ResponseEntity<Response<Service>>
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id){
        log.info("Deleting service by id: {}", id);
        Response<String> response = new Response<>();
        Optional<Service> service = this.serviceServ.findById(id);

        if (!service.isPresent()){
            log.info("Error deleting service by id: {}", id);
            response.getErrors().add("Error to remove the service. Data not found for id {}" + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.serviceServ.removeById(id);
        return ResponseEntity.ok(new Response<String>());
    }




    /**
     *Convert a service to DTO.
     *
     * @param service
     * @return ServiceDto
     */
    private ServiceDto convertServiceDto(Service service) {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(Optional.of(service.getId()));
        serviceDto.setPrice(service.getPrice());
        serviceDto.setStatus(service.getStatus().toString());
        serviceDto.setEvaluation(service.getEvaluation());
        serviceDto.setComments(service.getComments());
        serviceDto.setLocalization(service.getLocalization());
        serviceDto.setServiceDate(this.dateFormat.format(service.getServiceDate()));
        serviceDto.setMarineteId(service.getMarinete().getId());
        serviceDto.setUserId(service.getUser().getId());

        return serviceDto;
    }

    /**
     * Convert Dto to service.
     *
     * @param serviceDto
     * @param result
     * @return Service
     * @throws ParseException
     */
    private Service convertDtoToService(ServiceDto serviceDto, BindingResult result) throws ParseException {

        Service service = new Service();

        if(serviceDto.getId().isPresent()){
            Optional<Service> serv = this.serviceServ.findById(serviceDto.getId().get());
            if (serv.isPresent()){
                service = serv.get();
            } else {
                result.addError(new ObjectError("service", "Service not found."));
            }
        } else {
            service.setMarinete(new Marinete());
            service.getMarinete().setId(serviceDto.getMarineteId());
            service.setUser(new User());
            service.getUser().setId(serviceDto.getUserId());
        }

        service.setPrice(serviceDto.getPrice());
        service.setEvaluation(serviceDto.getEvaluation());
        service.setComments(serviceDto.getComments());
        service.setLocalization(serviceDto.getLocalization());
        service.setServiceDate(this.dateFormat.parse(serviceDto.getServiceDate()));

        if (EnumUtils.isValidEnum(StatusEnum.class, serviceDto.getStatus())){
            service.setStatus(StatusEnum.valueOf(serviceDto.getStatus()));
        } else {
            result.addError(new ObjectError("Status", "Status Invalid"));
        }

        return service;
    }

    /**
     *Validating the marinete. Verify if Marinete is present in database.
     *
     * @param serviceDto
     * @param result
     */
    private void validateMarinete(ServiceDto serviceDto, BindingResult result) {

        if(serviceDto.getMarineteId() == null){
            result.addError(new ObjectError("marinete", "Marinete not found;"));
        }

        log.info("Validating marinete id: {}", serviceDto.getMarineteId());
        Optional<Marinete> marinete = this.marineteService.findMarineteById(serviceDto.getMarineteId());
        if(!marinete.isPresent()){
            result.addError(new ObjectError("marinete", "Marinete not found. Id not exists in database"));
        }
    }

    /**
     *Validating the user. Verify if the User is present in database.
     *
     * @param serviceDto
     * @param result
     */
    private void validateUser(ServiceDto serviceDto, BindingResult result) {

        if(serviceDto.getUserId() == null){
            result.addError(new ObjectError("user", "User not found;"));
        }

        log.info("Validating User id: {}", serviceDto.getUserId());
        Optional<User> user = this.userService.findUserById(serviceDto.getUserId());
        if(!user.isPresent()){
            result.addError(new ObjectError("user", "User not found. Id not exists in database"));
        }
    }
}
