package com.ocorrencias.controller;

import com.ocorrencias.dto.OcorrenciaForm;
import com.ocorrencias.model.*;
import com.ocorrencias.service.OcorrenciaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @GetMapping
    public String listar(HttpSession session, Model model) {
        Usuario usuario = obterUsuarioLogado(session);
        if (usuario.getPapel() == Papel.FUNCIONARIO) {
            model.addAttribute("ocorrencias", ocorrenciaService.listarTodas());
        } else {
            model.addAttribute("ocorrencias", ocorrenciaService.listarDoUsuario(usuario));
        }
        return "ocorrencias/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("ocorrenciaForm", new OcorrenciaForm());
        model.addAttribute("categorias", Categoria.values());
        return "ocorrencias/formulario";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("ocorrenciaForm") OcorrenciaForm form, BindingResult result,
                        HttpSession session, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", Categoria.values());
            return "ocorrencias/formulario";
        }

        Usuario usuario = obterUsuarioLogado(session);

        if (!form.isIgnorarSimilares()) {
            List<Ocorrencia> similares = ocorrenciaService.buscaSimilares(form.getCategoria(), form.getLatitude(), form.getLongitude());
            if (!similares.isEmpty()) {
                model.addAttribute("similares", similares);
                return "ocorrencias/similares";
            }
        }

        Ocorrencia ocorrencia = ocorrenciaService.criarOcorrencia(form, usuario);
        return "redirect:/ocorrencias/" + ocorrencia.getProtocolo();
    }

    @GetMapping("/{protocolo}")
    public String detalhe(@PathVariable String protocolo, Model model) {
        model.addAttribute("ocorrencia", ocorrenciaService.buscarPorProtocolo(protocolo));
        model.addAttribute("statusList", StatusOcorrencia.values());
        return "ocorrencias/detalhe";
    }

    @PostMapping("/{protocolo}/confirmar")
    public String confirmar(@PathVariable String protocolo, HttpSession session, RedirectAttributes redirect) {
        ocorrenciaService.confirmar(protocolo, obterUsuarioLogado(session));
        redirect.addFlashAttribute("sucesso", "Obrigado! Sua confirmação foi registrada nesta ocorrência.");
        return "redirect:/ocorrencias/" + protocolo;
    }

    @PostMapping("/{protocolo}/status")
    public String alterarStatus(@PathVariable String protocolo, @RequestParam StatusOcorrencia status,
                                HttpSession session, RedirectAttributes redirect) {
        Usuario usuario = obterUsuarioLogado(session);
        if (usuario.getPapel() != Papel.FUNCIONARIO) {
            redirect.addFlashAttribute("erro", "Somente funcionários podem alterar o status.");
            return "redirect:/ocorrencias/" + protocolo;
        }
        ocorrenciaService.alterarStatus(protocolo, status, usuario);
        redirect.addFlashAttribute("sucesso", "Status atualizado.");
        return "redirect:/ocorrencias/" + protocolo;
    }

    private Usuario obterUsuarioLogado(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogado");
    }
}
