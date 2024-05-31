package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.Medico;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        EnderecoDTO endereco) {}