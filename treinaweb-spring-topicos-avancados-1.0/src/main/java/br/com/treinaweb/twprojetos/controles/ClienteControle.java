package br.com.treinaweb.twprojetos.controles;

import br.com.treinaweb.twprojetos.validadores.ClienteValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twprojetos.entidades.Cliente;
import br.com.treinaweb.twprojetos.enums.UF;
import br.com.treinaweb.twprojetos.repositorios.ClienteRepositorio;

import javax.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @InitBinder("cliente")
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new ClienteValidador(clienteRepositorio));
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("cliente/home");

        modelAndView.addObject("clientes", clienteRepositorio.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cliente/detalhes");

        modelAndView.addObject("cliente", clienteRepositorio.getOne(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("cliente/formulario");

        modelAndView.addObject("cliente", new Cliente());
        modelAndView.addObject("ufs", UF.values());

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cliente/formulario");

        modelAndView.addObject("cliente", clienteRepositorio.getOne(id));
        modelAndView.addObject("ufs", UF.values());

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String salvar(@Valid Cliente cliente, BindingResult resultado, ModelMap model) {
        if(resultado.hasErrors()){
            model.addAttribute("ufs", UF.values());
            return "cliente/formulario";
        }
        clienteRepositorio.save(cliente);

        return "redirect:/clientes";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        clienteRepositorio.deleteById(id);

        return "redirect:/clientes";
    }
    
}
