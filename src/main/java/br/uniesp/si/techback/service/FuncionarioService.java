package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    // CREATE
    public Funcionario salvar(Funcionario func) {
        return repository.save(func);
    }

    // READ
    public List<Funcionario> listar() {
        return repository.findAll();
    }

    // UPDATE
    public Funcionario atualizar(Long id, Funcionario funcionario) {

        Funcionario existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário inexistente"));

        existente.setNome(funcionario.getNome());
        existente.setCargo(funcionario.getCargo());
        existente.setSalario(funcionario.getSalario());

        return repository.save(existente);
    }

    // DELETE
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    // READ por ID
    public Funcionario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }
}
