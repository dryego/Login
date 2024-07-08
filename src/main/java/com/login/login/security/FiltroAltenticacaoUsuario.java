package com.login.login.security;

import com.login.login.model.Usuario;
import com.login.login.model.UsuarioDetalhes;
import com.login.login.repository.UsuarioRespository;
import com.login.login.service.jwt.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class FiltroAltenticacaoUsuario extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRespository usuarioRespository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(verificaEndPointPublico(request)){
            String token = recuperaToken(request);
            if(token != null){
                String subject = jwtTokenService.pegarToken(token);
                Usuario usuario = usuarioRespository.findByEmail(subject).get();
                UsuarioDetalhes usuarioDetalhes = new UsuarioDetalhes(usuario);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                usuarioDetalhes.getUsername(),
                                null,
                                usuarioDetalhes.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
               throw new RuntimeException("Token não existe.");
            }
        }
        filterChain.doFilter(request,response);

    }

    private String recuperaToken(HttpServletRequest request) {
        String autorizacaoHeader = request.getHeader("Authorization");
        if(autorizacaoHeader != null){
            return autorizacaoHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean verificaEndPointPublico(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList("/api/usuario/login", "/api/usuario").contains(requestURI);
    }
}
