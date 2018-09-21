package br.com.marinete.api.security.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_ROLE = "role";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Get the username (email) contained in the JWT token.
     *
     * @param token
     * @return String
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * Returns the expiration date of a JWT token.
     *
     * @param token
     * @return Date
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * Create a new token (refresh).
     *
     * @param token
     * @return String
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * Checks and returns whether a JWT token is valid.
     *
     * @param token
     * @return boolean
     */
    public boolean tokenValid(String token) {
        return !tokenExpired(token);
    }

    /**
     * Returns a new JWT token based on user data.
     *
     * @param userDetails
     * @return String
     */
    public String getToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
        claims.put(CLAIM_KEY_CREATED, new Date());

        return generateToken(claims);
    }

    /**
     * 1
     *
     * 87/5000
     * Performs the parsing of the JWT token to extract the information contained in the
     * his body.
     *
     * @param token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Returns the expiration date based on the current date.
     *
     * @return Date
     */
    private Date getExpireDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * Checks if a JTW token is expired.
     *
     * @param token
     * @return boolean
     */
    private boolean tokenExpired(String token) {
        Date dataExpiracao = this.getExpirationDateFromToken(token);
        if (dataExpiracao == null) {
            return false;
        }
        return dataExpiracao.before(new Date());
    }

    /**
     * Generates a new JWT token containing the supplied data (claims).
     *
     * @param claims
     * @return String
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(getExpireDate())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
