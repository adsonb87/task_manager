package com.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.model.Tarefa;
import com.taskmanager.model.Usuario;
import com.taskmanager.repository.TarefaRepository;
import com.taskmanager.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/tarefas", produces = "application/json")
public class TarefaController {

  @GetMapping
  public ResponseEntity<Page<Tarefa>> listarTarefas(
      @PageableDefault(size = 10, page = 0, sort = { "nome" }) Pageable pageable) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String emailUserLogado = authentication.getName();

    Usuario usuario = usuarioRepository.findByEmail(emailUserLogado);

    Page<Tarefa> tarefas = tarefaRepository.findByUsuario(usuario, pageable);

    return ResponseEntity.status(HttpStatus.OK).body(tarefas);
  }

  @PostMapping
  public ResponseEntity<Tarefa> criarTarefa(@Valid @RequestBody Tarefa tarefa) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String emailUsuarioLogado = authentication.getName();

    Usuario usuarioLogado = usuarioRepository.findByEmail(emailUsuarioLogado);
    tarefa.setUsuario(usuarioLogado);

    Tarefa novaTarefa = tarefaRepository.save(tarefa);

    return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
  }

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private TarefaRepository tarefaRepository;
}
