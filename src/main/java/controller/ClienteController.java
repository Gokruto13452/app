package com.empresa.app.controller;

import com.empresa.app.model.Cliente;
import com.empresa.app.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepo;

    // LISTAR COM FILTROS
    @GetMapping
    public String listarClientes(@RequestParam(required = false) String nome,
                                 @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date inicio,
                                 @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fim,
                                 Model model) {

        List<Cliente> clientes;

        if (nome != null && !nome.isEmpty()) {
            clientes = clienteRepo.findByNomeContainingIgnoreCase(nome);
        } else if (inicio != null && fim != null) {
            clientes = clienteRepo.findByDataCadastroBetween(inicio, fim);
        } else {
            clientes = clienteRepo.findAll();
        }

        model.addAttribute("clientes", clientes);
        return "clientes-lista";
    }

    // FORMULÁRIO NOVO CLIENTE
    @GetMapping("/novo")
    public String novoClienteForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes-form";
    }

    // SALVAR (NOVO OU EDITAR)
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente) {
        clienteRepo.save(cliente);
        return "redirect:/clientes";
    }

    // EDITAR CLIENTE
    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("cliente", cliente);
        return "clientes-form";
    }

    // EXCLUIR CLIENTE
    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable Long id) {
        clienteRepo.deleteById(id);
        return "redirect:/clientes";
    }
}
