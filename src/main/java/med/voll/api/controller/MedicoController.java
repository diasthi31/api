package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosAtualizacaoMedico;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosDetalhamentoMedico;
import med.voll.api.dto.DadosListagemMedico;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired //INJEÇÃO DE DEPENDÊNCIA, FAZENDO COM QUE O SPRING GERENCIE E FAÇA TUDO À RESPEITO
    private MedicoRepository repository;

    @PostMapping//MÉTODO PARA RECEBER UM JSON EM UM MÉTODO POST
    @Transactional //É NECESSÁRIO POR ESTAR FAZENDO UMA TRANSIÇÃO NO BANCO, NO CASO, UMA INSERÇÃO
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) { //MÉTODO PARA CRIAR ALTOMATICAMENTE UMA URI
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); //PEGA O CAMINHO ATUAL E ADICIONA O PARÂMETRO

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
        //NO MÉTODO GET, DEVE RETORNAR O CÓDIGO 201, UMA REPRESENTACAO OBJETO QUE FOI CRIADO E A URI, O CAMINHO DO PATH
    }

    @GetMapping //MÉTODO PARA RETORNAR REGISTROS DO BANCO DE DADOS
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        //NÃO PRECISA SALVAR DNV, JÁ QUE É TRANSACIOANAL, A JPA ENTENDE QUE OCORREU A MUDANÇA E FAZ DE FORMA AUTOMÁTICA

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
        //AO ATUALIZAR ELE RETORNA TODOS OS DADOS DO MÉDICO
    }

//    //EXCLUSÃO FÍSICA
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void excluir1(@PathVariable Long id) { //DIZ QUE O PARÂMETRO É O DO CAMINHO
//        repository.deleteById(id);
//    }

    //EXCLUSÃO LÓGICA
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) { //DIZ QUE O PARÂMETRO É O DO CAMINHO
        var medico = repository.getReferenceById(id);
        medico.excluir();
        //COMO TEM TRANSACTIONAL, JPA ATUALIZA AUTOMÁTICO

        return ResponseEntity.noContent().build(); //MÉTODO MAIS ADEQUADO PARA EXCLUSAO, RETORNA O CODIGO 204 DO PROTOCOLO HTTP
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) { //DIZ QUE O PARÂMETRO É O DO CAMINHO
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}