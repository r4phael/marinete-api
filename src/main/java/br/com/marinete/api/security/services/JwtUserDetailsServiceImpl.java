package br.com.marinete.api.security.services;

import br.com.marinete.api.entities.User;
import br.com.marinete.api.security.JwtUserFactory;
import br.com.marinete.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(username);

        if (user.isPresent()) {
            return JwtUserFactory.create(user.get());
        }

        throw new UsernameNotFoundException("Email doesn't exists.");
    }


}
