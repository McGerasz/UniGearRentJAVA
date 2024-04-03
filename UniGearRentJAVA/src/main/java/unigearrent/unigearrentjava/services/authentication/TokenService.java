package unigearrent.unigearrentjava.services.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import unigearrent.unigearrentjava.models.User;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Autowired
   private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    public String generateJwt(Authentication auth, User user)
    {
        Instant now = Instant.now();
        Duration validityDuration = Duration.ofMinutes(30);
        Instant expirationTime = now.plus(validityDuration);

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(String.valueOf(user.getUserId()))
                .expiresAt(expirationTime)
                .claim("http://schemas.microsoft.com/ws/2008/06/identity/claims/role", scope.toString().toLowerCase().replace(scope.toString().toLowerCase().charAt(0), scope.toString().charAt(0)))
                .claim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress", user.getEmail())
                .claim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name", "")
                .claim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier", "")
                .claim("jti", UUID.randomUUID().toString())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Jwt decodeJwt(String jwt)
    {
        var decode = jwtDecoder.decode(jwt);
        System.out.println(decode.getSubject());
        System.out.println(decode.getClaims());

        return decode;
    }
}
