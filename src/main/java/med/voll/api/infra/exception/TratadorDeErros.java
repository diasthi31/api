package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//ANOTAÇÃO PARA CONTROLADOR DE EXCESSOES
@RestControllerAdvice
public class TratadorDeErros {

    //CHAMADO PARA TRATAR A EXCESSÃO QUANDO NÃO ENCONTRAR O QUE FOI RESQUISITADO, DEVOLVENDO ERRO 404 AO INVÉS DE 500
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    //TRATAR ERRO DE VIOLAÇÃO DE ENTRADA DE DADOS E RECEBE A EXCEPTION QUE OCORREU
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors(); //LISTA COM ERROS

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    //DTO DOS DADOS DO ERRO
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}