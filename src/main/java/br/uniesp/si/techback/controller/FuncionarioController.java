package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
@Slf4j
public class FuncionarioController {

    private final FuncionarioService service;

    @GetMapping
    public List<Funcionario> listar() {
        log.info("Listando todos os funcionários");
        List<Funcionario> funcionarios = service.listar();
        log.debug("Total de funcionários encontrados: {}", funcionarios.size());
        return funcionarios;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        try {
            Funcionario funcionario = service.buscarPorId(id);
            log.debug("Funcionário encontrado: {}", funcionario);
            return ResponseEntity.ok(funcionario);
        } catch (Exception e) {
            log.error("Erro ao buscar funcionário com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Funcionario> criar(@Valid @RequestBody Funcionario funcionario) {
        log.info("Recebida requisição para criar novo funcionário: {}", funcionario.getNome());
        try {
            Funcionario funcionarioSalvo = service.salvar(funcionario);
            log.info("Funcionário criado com sucesso. ID: {}, Nome: {}", funcionarioSalvo.getId(), funcionarioSalvo.getNome());
            
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(funcionarioSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo funcionário: {}", location);
            
            return ResponseEntity.created(location).body(funcionarioSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar funcionário: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable Long id, @Valid @RequestBody Funcionario funcionario) {
        log.info("Atualizando funcionário com ID {}: {}", id, funcionario);
        try {
            Funcionario funcionarioAtualizado = service.atualizar(id, funcionario);
            log.debug("Funcionário ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar funcionário ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Excluindo funcionário com ID: {}", id);
        try {
            service.excluir(id);
            log.debug("Funcionário com ID {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir funcionário com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

}
