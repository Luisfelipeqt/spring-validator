package br.com.treinaweb.twprojetos.controles;

import br.com.treinaweb.twprojetos.validadores.PessoaValidador;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice(assignableTypes = {FuncionarioControle.class, ClienteControle.class})
public class PessoaControle {

    @InitBinder(value = {"funcionario", "cliente"})
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new PessoaValidador());
    }
}
