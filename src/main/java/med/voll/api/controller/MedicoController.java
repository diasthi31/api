package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosListagemMedico;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired //INJEÇÃO DE DEPENDÊNCIA, FAZENDO COM QUE O SPRING GERENCIE E FAÇA TUDO À RESPEITO
    private MedicoRepository repository;

    @PostMapping//MÉTODO PARA RECEBER UM JSON EM UM MÉTODO POST
    @Transactional //É NECESSÁRIO POR ESTAR FAZENDO UMA TRANSIÇÃO NO BANCO, NO CASO, UMA INSERÇÃO
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping //MÉTODO PARA RETORNAR REGISTROS DO BANCO DE DADOS
    public Page<DadosListagemMedico> listar(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

}