package br.com.marinete.api.security;

import br.com.marinete.api.entities.User;
import br.com.marinete.api.enums.ProfileEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sun.java2d.cmm.Profile;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    /**
     * Convert and gen a JwtUser based on user data.
     *
     * @param user
     * @return JwtUser
     */
    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword(),
                mapToGrantedAuthorities(user.getProfile()));
    }

    /**
     * Converts the user profile to the format used by Spring Security.
     *
     * @param profileEnum
     * @return List<GrantedAuthority>
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
        return authorities;
    }
}
