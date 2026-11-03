package com.ocorrencias.service;

import com.ocorrencias.dto.OcorrenciaForm;
import com.ocorrencias.model.*;
import com.ocorrencias.repository.ConfirmacaoRepository;
import com.ocorrencias.repository.OcorrenciaRepository;
import com.ocorrencias.repository.SetorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OcorrenciaService {

    // aprox. 150 metros em graus de latitude/longitude
    private static final double RAIO = 0.0015;
    private static final Path PASTA_FOTOS = Paths.get("uploads");

    private final OcorrenciaRepository ocorrenciaRepository;
    private final SetorRepository setorRepository;
    private final ConfirmacaoRepository confirmacaoRepository;
    private final HistoricoStatusService historicoStatusService;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, SetorRepository setorRepository,
                             ConfirmacaoRepository confirmacaoRepository, HistoricoStatusService historicoStatusService) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.setorRepository = setorRepository;
        this.confirmacaoRepository = confirmacaoRepository;
        this.historicoStatusService = historicoStatusService;
    }

    public List<Ocorrencia> buscaSimilares(Categoria categoria, double lat, double lng) {
        return ocorrenciaRepository.buscaSimilares(categoria, lat - RAIO, lat + RAIO, lng - RAIO, lng + RAIO);
    }

    public Ocorrencia criarOcorrencia(OcorrenciaForm form, Usuario usuario) {
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setCategoria(form.getCategoria());
        ocorrencia.setDescricao(form.getDescricao());
        ocorrencia.setLatitude(form.getLatitude());
        ocorrencia.setLongitude(form.getLongitude());
        ocorrencia.setFotoUrl(armazenarFoto(form.getFoto()));
        ocorrencia.setProtocolo(gerarProtocolo());
        ocorrencia.setSetor(setorRepository.buscarPorCategoria(form.getCategoria()).orElse(null));
        ocorrencia.setStatus(StatusOcorrencia.RECEBIDO);
        ocorrencia.setDataCriacao(LocalDateTime.now());
        ocorrencia.setUsuario(usuario);

        ocorrencia = ocorrenciaRepository.save(ocorrencia);
        historicoStatusService.registrar(ocorrencia, usuario, null, StatusOcorrencia.RECEBIDO);
        return ocorrencia;
    }

    public void confirmar(String protocolo, Usuario usuario) {
        Ocorrencia ocorrencia = buscarPorProtocolo(protocolo);
        if (!confirmacaoRepository.existsByUsuarioAndOcorrencia(usuario, ocorrencia)) {
            confirmacaoRepository.save(new Confirmacao(usuario, ocorrencia));
        }
    }

    public void alterarStatus(String protocolo, StatusOcorrencia novoStatus, Usuario funcionario) {
        Ocorrencia ocorrencia = buscarPorProtocolo(protocolo);
        StatusOcorrencia anterior = ocorrencia.getStatus();
        if (anterior == novoStatus) {
            return;
        }
        ocorrencia.setStatus(novoStatus);
        ocorrenciaRepository.save(ocorrencia);
        historicoStatusService.registrar(ocorrencia, funcionario, anterior, novoStatus);
    }

    public Ocorrencia buscarPorProtocolo(String protocolo) {
        return ocorrenciaRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada: " + protocolo));
    }

    public List<Ocorrencia> listarDoUsuario(Usuario usuario) {
        return ocorrenciaRepository.findByUsuarioOrderByDataCriacaoDesc(usuario);
    }

    public List<Ocorrencia> listarTodas() {
        return ocorrenciaRepository.findAllByOrderByDataCriacaoDesc();
    }

    private String armazenarFoto(MultipartFile foto) {
        if (foto == null || foto.isEmpty()) {
            return null;
        }
        try {
            Files.createDirectories(PASTA_FOTOS);
            String original = foto.getOriginalFilename();
            String extensao = original != null && original.contains(".")
                    ? original.substring(original.lastIndexOf('.')) : ".jpg";
            String nome = UUID.randomUUID() + extensao;
            foto.transferTo(PASTA_FOTOS.resolve(nome).toAbsolutePath());
            return "/uploads/" + nome;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a foto", e);
        }
    }

    private String gerarProtocolo() {
        long numero = ocorrenciaRepository.count() + 1;
        return String.format("OC%d%05d", LocalDateTime.now().getYear(), numero);
    }
}
