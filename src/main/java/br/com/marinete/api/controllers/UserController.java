package br.com.marinete.api.controllers;


import br.com.marinete.api.dtos.UserDto;
import br.com.marinete.api.entities.User;
import br.com.marinete.api.response.Response;
import br.com.marinete.api.services.UserService;
import br.com.marinete.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public UserController() {
    }

    /**
     * Update the user data
     *
     * @param id
     * @param userDto
     * @param result
     * @return ResponseEntity<Response<UserDto>>
     * @throws java.security.NoSuchAlgorithmException
     */

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<UserDto>> update (@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto,
                                                     BindingResult result) throws NoSuchAlgorithmException {
        log.info("Updating user: {}", userDto.toString());
        Response<UserDto> response = new Response<>();

        Optional<User> user = this.userService.findUserById(id);
        if (!((Optional) user).isPresent()){
            result.addError((new ObjectError("user", "User not Found")));
        }

        this.updateUserData(user.get(), userDto, result);

        if(result.hasErrors()){
            log.error(("Error in validating User: {}"), result.getAllErrors());
            result.getAllErrors().forEach(objectError -> response.getErrors().add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.userService.persist(user.get());
        response.setData(this.convertUserToDto(user.get()));

        return ResponseEntity.ok(response);

    }

    /**
     * Return one User DTO.
     *
     * @param user
     * @return UserDto
     */

    private UserDto convertUserToDto(User user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setCpf(user.getCpf());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());

        return userDto;
    }

    /**
     *Update the data of user with data found in DTO object.
     *
     * @param user
     * @param userDto
     * @param result
     * @throws NoSuchAlgorithmException
     */

    private void updateUserData(User user, UserDto userDto, BindingResult result) throws NoSuchAlgorithmException{

        user.setName(userDto.getName());

        if(!user.getEmail().equals(userDto.getEmail())){
            this.userService.findByEmail(userDto.getEmail())
                    .ifPresent(user1 -> result.addError(new ObjectError("email", "Email already exists")));
                    user.setEmail(userDto.getEmail());
        }

        if(userDto.getPassword().isPresent()){
            user.setPassword(PasswordUtils.genBcrypt(userDto.getPassword().get()));
        }


    }


}
