package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.endereco.EnderecoDTO;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //DIZ PARA O SPRING NÃO SUBSTITUIR A CONFIGURAÇÃO DE BANCO DE DADOS E UTILIZAR O MEU
@ActiveProfiles("test") //PARA A CLASSE DE TESTE UTILIZAR O APPLICATION-TEST.PROPERTIES
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando único médico cadastrado não está disponível na data.")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = cadastraMedico("Thiago", "diasthi31@gmail.com", "3101", Especialidade.CARDIOLOGIA);
        var paciente = paciente("Amanda", "amandalino15@gmail.com", "256542000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isNull();
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data));
    }

    private Medico cadastraMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
                em.persist(medico);
                return medico;
    }

    private Paciente paciente(String nome, String email, String cpf) {
        var paciente = new Paciente(
                nome,
                email,
                "24988888888",
                cpf,
                endereco()
        );
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "24988888888",
                crm,
                especialidade,
                enderecoDTO()
        );
    }

    private Endereco endereco() {
        return new Endereco(
                "Rua Mato Grosso",
                "Quitandinha",
                "25652-2200",
                "Petrópolis",
                "RJ",
                null,
                null
        );
    }

    private EnderecoDTO enderecoDTO() {
        return new EnderecoDTO(
                "Rua Mato Grosso",
                "Quitandinha",
                "25652-2200",
                "Petrópolis",
                "RJ",
                null,
                null
        );
    }

}