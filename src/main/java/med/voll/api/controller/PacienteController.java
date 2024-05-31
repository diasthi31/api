package med.voll.api.controller;

import med.voll.api.dto.PacienteDTO;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}