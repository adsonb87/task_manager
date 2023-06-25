package com.taskmanager.controller;


import com.taskmanager.model.Usuario;
import com.taskmanager.repository.UsuarioRepository;
import com.taskmanager.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/usuarios", produces = "application/json")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    private ResponseEntity<Object> salvarUsuario(@RequestBody Usuario usuario){

        UserDetails usuarioBD = usuarioRepository.findByEmail(usuario.getEmail());

        if(Objects.nonNull(usuarioBD)){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body("Já existe usuário cadastrado com este e-mail");
        }

        String senha = usuario.getSenha();

        BCryptPasswordEncoder encoder = authenticationService.getPasswordEncoder();

        usuario.setSenha(encoder.encode(senha));

        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuario));
    }

    @GetMapping
    private ResponseEntity<Object> listarUsuarios(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findAll(pageable));
    }

    @PutMapping("/{id}")
    private ResponseEntity<Usuario> editarUsuario(@PathVariable("id") long id, @RequestBody Usuario usuario){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if(usuarioOptional.isPresent()){
            usuario.setIdUsuario(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(usuarioRepository.save(usuario));
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deletarUsuario(@PathVariable("id") long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if(usuarioOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso !!");
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> buscarUsuarioId(@PathVariable("id")long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if(usuarioOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional);
    }

    /*Recuperar dados do usuario logado*/
    @GetMapping("/teste")
    private ResponseEntity<Object> teste(@CurrentSecurityContext(expression = "authentication.getPrincipal()") Usuario usuario){

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

}
