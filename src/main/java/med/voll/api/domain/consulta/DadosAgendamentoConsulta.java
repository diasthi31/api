package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        //SERVE PARA MAPEAR "APELIDO" QUE PODE SER ACEITO PELO JSON QUE SERÁ CONSUMIDO PELA API
        @JsonAlias({"id_paciente", "paciente_id"}) Long idMedico,

        @NotNull
        @JsonAlias({"paciente_id", "id_paciente"}) Long idPaciente,

        @NotNull
        @Future
        @JsonAlias({"data_da_consulta"})
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") //INFORMAR OUTRO PADRÃO DE DATA E HORA QUE PODE SER ACEITO TAMBÉM
        LocalDateTime data,

        Especialidade especialidade) {
}