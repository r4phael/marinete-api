package br.com.marinete.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.marinete.api.entities"})
public class MarineteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarineteApiApplication.class, args);
    }

    @Configuration
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //Disable Security
            http.authorizeRequests().antMatchers("/").permitAll();
            //The filter is waiting for csrfToken on header or query parameter
            http.csrf().disable();
        }

    }
}
