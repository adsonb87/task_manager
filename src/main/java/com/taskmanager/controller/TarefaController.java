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

import com.taskmanager.dto.TarefaDTO;
import com.taskmanager.dto.UsuarioDTO;
import com.taskmanager.model.Tarefa;
import com.taskmanager.model.Usuario;
import com.taskmanager.repository.TarefaRepository;
import com.taskmanager.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/tarefas", produces = "application/json")
public class TarefaController {

  @GetMapping
  public ResponseEntity<Page<TarefaDTO>> listarTarefas(
      @PageableDefault(size = 10, page = 0, sort = { "nome" }) Pageable pageable) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String emailUserLogado = authentication.getName();

    Usuario usuario = usuarioRepository.findByEmail(emailUserLogado);

    Page<Tarefa> tarefas = tarefaRepository.findByUsuario(usuario, pageable);
    Page<TarefaDTO> tarefasDTOs = tarefas.map(this::convertToDTO);

    return ResponseEntity.status(HttpStatus.OK).body(tarefasDTOs);
  }

  @PostMapping
  public ResponseEntity<TarefaDTO> criarTarefa(@Valid @RequestBody TarefaDTO tarefaDTO) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String emailUsuarioLogado = authentication.getName();

    Usuario usuarioLogado = usuarioRepository.findByEmail(emailUsuarioLogado);
    Tarefa tarefa = convertToEntity(tarefaDTO);
    tarefa.setUsuario(usuarioLogado);

    Tarefa novaTarefa = tarefaRepository.save(tarefa);
    TarefaDTO novaTarefaDTO = convertToDTO(novaTarefa);

    return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefaDTO);
  }

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private TarefaRepository tarefaRepository;

  private TarefaDTO convertToDTO(Tarefa tarefa) {
    UsuarioDTO usuarioDTO = new UsuarioDTO(
        tarefa.getUsuario().getNome(),
        tarefa.getUsuario().getSobrenome(),
        tarefa.getUsuario().getEmail(),
        null // O token pode ser definido de acordo com a necessidade
    );

    return new TarefaDTO(
        tarefa.getId(),
        tarefa.getNome(),
        tarefa.getDescricao(),
        tarefa.getPrioridade(),
        tarefa.getStatus(),
        tarefa.getDataInicio(),
        tarefa.getDataFinal(),
        usuarioDTO);
  }

  private Tarefa convertToEntity(TarefaDTO tarefaDTO) {
    Tarefa tarefa = new Tarefa();
    tarefa.setId(tarefaDTO.getId());
    tarefa.setNome(tarefaDTO.getNome());
    tarefa.setDescricao(tarefaDTO.getDescricao());
    tarefa.setPrioridade(tarefaDTO.getPrioridade());
    tarefa.setStatus(tarefaDTO.getStatus());
    tarefa.setDataInicio(tarefaDTO.getDataInicio());
    tarefa.setDataFinal(tarefaDTO.getDataFinal());

    Usuario usuario = new Usuario();
    usuario.setNome(tarefaDTO.getUsuario().getNome());
    usuario.setSobrenome(tarefaDTO.getUsuario().getSobrenome());
    usuario.setEmail(tarefaDTO.getUsuario().getEmail());
    tarefa.setUsuario(usuario);

    return tarefa;
  }

}
