package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.EnderecoDTO;

public record DadosCadastroMedico(
        @NotBlank //VALIDAÇÃO DE ENTRADA
        String nome,
        @NotBlank //APENAS PARA STRINGS
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") //REGEX (EXPRESSÃO REGULAR)
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid //VALIDAR OUTRO OBJETO
        EnderecoDTO endereco) {}