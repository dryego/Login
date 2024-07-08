package com.login.login.service.usuario;

import com.login.login.dtos.jwt.JwtTokenDTO;
import com.login.login.dtos.usuario.CriarUsuarioDTO;
import com.login.login.dtos.usuario.LoginUsuarioDTO;
import com.login.login.model.Nivel;
import com.login.login.model.Usuario;
import com.login.login.model.UsuarioDetalhes;
import com.login.login.repository.UsuarioRespository;
import com.login.login.security.ConfiguracaoSeguranca;
import com.login.login.service.jwt.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRespository usuarioRespository;

    @Autowired
    private ConfiguracaoSeguranca configuracaoSeguranca;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    public void criarUsuario(CriarUsuarioDTO usuarioDTO){
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuarioDTO.nome());
        novoUsuario.setEmail(usuarioDTO.email());
        novoUsuario.setSenha(configuracaoSeguranca.passwordEncoder().encode(usuarioDTO.senha()));

        Nivel nivel = new Nivel(usuarioDTO.nivel());
        novoUsuario.setNivel(Collections.singletonList(nivel));

        usuarioRespository.save(novoUsuario);
    }

    public JwtTokenDTO autenticarUsuario (LoginUsuarioDTO login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(login.email(), login.senha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UsuarioDetalhes usuarioDetalhes = (UsuarioDetalhes) authentication.getPrincipal();
        return new JwtTokenDTO(jwtTokenService.gerarToken(usuarioDetalhes));
    }
}
