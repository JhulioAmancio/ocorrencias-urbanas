package com.ocorrencias.controller;

import com.ocorrencias.dto.UsuarioForm;
import com.ocorrencias.exception.EmailJaCadastradoException;
import com.ocorrencias.model.Usuario;
import com.ocorrencias.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String inicio(HttpSession session) {
        return session.getAttribute("usuarioLogado") != null ? "redirect:/ocorrencias" : "redirect:/login";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuarioForm", new UsuarioForm());
        return "usuario/cadastro";
    }

    @PostMapping("/cadastro")
    public String criarConta(@Valid @ModelAttribute("usuarioForm") UsuarioForm form, BindingResult result,
                             RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "usuario/cadastro";
        }
        try {
            usuarioService.criarConta(form);
        } catch (EmailJaCadastradoException e) {
            result.rejectValue("email", "email.existente", "Este e-mail já está cadastrado");
            return "usuario/cadastro";
        }
        redirect.addFlashAttribute("sucesso", "Conta criada com sucesso! Faça seu login.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }

    @PostMapping("/login")
    public String entrar(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model) {
        Usuario usuario = usuarioService.autenticar(email, senha);
        if (usuario == null) {
            model.addAttribute("erro", "E-mail ou senha inválidos");
            model.addAttribute("email", email);
            return "usuario/login";
        }
        session.setAttribute("usuarioLogado", usuario);
        return "redirect:/ocorrencias";
    }

    @GetMapping("/logout")
    public String sair(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
