package med.voll.api.model;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.dto.DadosCadastroMedico;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter //ANOTAÇÕES DO LOMBOCK PARA GERAR TODOS OS MÉTODOS ABAIXO AUTOMATICAMENTE EM TEMPO DE COMPILAÇÃO
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedico dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }
}