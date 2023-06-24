package com.taskmanager.controller;


import com.taskmanager.model.Usuario;
import com.taskmanager.repository.UsuarioRepository;
import com.taskmanager.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/usuarios", produces = "application/json")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    private ResponseEntity<Object> salvarUsuario(@RequestBody Usuario usuario){
        long idUsuario = usuario.getIdUsuario();

        if(idUsuario == 0){
            UserDetails usuarioBD = usuarioRepository.findByEmail(usuario.getEmail());

            if(Objects.nonNull(usuarioBD)){
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                        .body("Já existum usuário cadastrado com este e-mail");
            }

            String senha = usuario.getSenha();

            BCryptPasswordEncoder encoder = authenticationService.getPasswordEncoder();

            usuario.setSenha(encoder.encode(senha));
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuario));
    }







}
