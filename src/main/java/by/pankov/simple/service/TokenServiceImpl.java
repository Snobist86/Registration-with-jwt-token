package by.pankov.simple.service;

import by.pankov.simple.model.User;
import by.pankov.simple.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    private UserRepository userRepository;

    @Value("${jwt.secret.key}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Autowired
    public TokenServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String generateToken(String email) {
        Date createdDate = DefaultClock.INSTANCE.now();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(createdDate)
                .setExpiration(new Date(createdDate.getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token) {
        Claims claims = getClaims(token);
        Optional<User> email = userRepository.findByEmail(claims.getSubject());
        return (email.isPresent() && claims.getExpiration().before(DefaultClock.INSTANCE.now()));
    }

    @Override
    public String getEmail(String token){
       return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
