package med.voll.api.controller;

import med.voll.api.dto.DadosListagemPaciente;
import med.voll.api.dto.PacienteDTO;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    //MÉTODO PARA RECEBER UM JSON EM UM MÉTODO POST
    @PostMapping
    public void cadastrar(@RequestBody PacienteDTO dados) {
        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(page = 0, size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemPaciente::new);
    }

}