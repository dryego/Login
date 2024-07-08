package com.login.login.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.login.login.model.UsuarioDetalhes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {
    @Value("${token.jwt.secret.key}")
    private String senhaJwt;

    @Value("${token.jwt.secret.issuer}")
    private String issuer;

    public String gerarToken(UsuarioDetalhes usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(senhaJwt);
            return JWT.create()
                    .withIssuer(issuer)
                    .withIssuedAt(dataCriacao())
                    .withExpiresAt(dataExpiracao())
                    .withSubject(usuario.getUsername())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao jerar token: ", exception);
        }
    }

    public String pegarToken(String token){
        try {
            Algorithm algorithm =Algorithm.HMAC256(senhaJwt);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token invalido ou expirado.");
        }
    }

    private Instant dataExpiracao() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(8).toInstant();
    }

    private Instant dataCriacao() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }
}
