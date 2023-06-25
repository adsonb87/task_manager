package com.taskmanager.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.taskmanager.model.StatusTarefa;
import com.taskmanager.model.Tarefa;
import com.taskmanager.model.Usuario;
import com.taskmanager.repository.TarefaRepository;
import com.taskmanager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping
    private ResponseEntity<Object> salvarTarefa(@RequestBody Tarefa tarefa, @CurrentSecurityContext(expression = "authentication.getPrincipal()") Usuario usuario){
        if(tarefa.getIdTarefa() == null){
           return ResponseEntity.status(HttpStatus.OK).body(tarefaRepository.save(tarefa));
        }
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Tarefa já cadastrada !!");
    }

    @GetMapping
    private ResponseEntity<Object> listarTarefas(@PageableDefault(size = 10, page = 0, sort = {"titulo"})Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(tarefaRepository.findAll(pageable));
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> editarTarefa(@PathVariable("id") long id, @RequestBody Tarefa tarefa){
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

        if(tarefaOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        tarefa.setIdTarefa(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(tarefaRepository.save(tarefa));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deletarTarefa(@PathVariable("id") long id){
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

        if(tarefaOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        Tarefa tarefa = tarefaOptional.get();
        tarefaRepository.delete(tarefa);
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa deletada com sucesso !!");
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> buscarTarefaId(@PathVariable("id") long id){
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

        if(tarefaOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(tarefaOptional.get());
    }

    @PutMapping("/status/{id}")
    private ResponseEntity<Object> editarStatusTarefa(@PathVariable("id") long id, @RequestBody String statusTarefa){
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

        if(tarefaOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        Tarefa tarefa = tarefaOptional.get();

        if(statusTarefa.equals("concluido")){
            tarefa.setStatusTarefa(StatusTarefa.concluido);
        }else if(statusTarefa.equals("naoConcluido")){
            tarefa.setStatusTarefa(StatusTarefa.naoConcluido);
        }

        System.out.println(statusTarefa);

        return ResponseEntity.status(HttpStatus.FOUND).body(statusTarefa);
    }


}
