package com.taskmanager.controller;

import com.taskmanager.model.Usuario;
import com.taskmanager.repository.TarefaRepository;
import com.taskmanager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/tarefas", produces = "application/json")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*Recuperar dados do usuario logado*/
    @GetMapping("/teste")
    private ResponseEntity<Object> teste(@CurrentSecurityContext(expression = "authentication.getPrincipal()") Usuario usuario){

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }
}
