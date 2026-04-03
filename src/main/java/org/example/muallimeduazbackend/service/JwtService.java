package org.example.muallimeduazbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.entity.News;
import org.example.muallimeduazbackend.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.example.muallimeduazbackend.constant.NumberConstants.TOKEN_EXPARATION_DATE;
import static org.example.muallimeduazbackend.constant.TextConstants.BEARER_SEPERATOR;
import static org.example.muallimeduazbackend.constant.TextConstants.SECRET_KEY;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserService userService;
    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .claim("id", user.getId())
                .setExpiration(new Date(new Date().getTime() + TOKEN_EXPARATION_DATE))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }

    public User decodeToken(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(getCleanToken(token)).getBody();
        var existingUser = userService.findById((Integer) claims.get("id"));
        if(existingUser == null){
            throw new RuntimeException("person not found");
        }
        return existingUser;
    }

    public String getCleanToken(String token){
        return token.split(BEARER_SEPERATOR)[1];
    }
}
