package com.login.login.controller.usuario;

import com.login.login.dtos.jwt.JwtTokenDTO;
import com.login.login.dtos.usuario.CriarUsuarioDTO;
import com.login.login.dtos.usuario.LoginUsuarioDTO;
import com.login.login.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> postLoginUsuario(@RequestBody LoginUsuarioDTO loginUsuarioDTO){
        JwtTokenDTO token = usuarioService.autenticarUsuario(loginUsuarioDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> postCriarUsuario(@RequestBody CriarUsuarioDTO criarUsuarioDTO){
        usuarioService.criarUsuario(criarUsuarioDTO);
        return  new ResponseEntity<>(HttpStatus.CREATED);

    }

}
