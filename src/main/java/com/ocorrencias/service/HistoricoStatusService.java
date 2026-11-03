package com.ocorrencias.service;

import com.ocorrencias.model.HistoricoStatus;
import com.ocorrencias.model.Ocorrencia;
import com.ocorrencias.model.StatusOcorrencia;
import com.ocorrencias.model.Usuario;
import com.ocorrencias.repository.HistoricoStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class HistoricoStatusService {

    private final HistoricoStatusRepository historicoRepository;

    public HistoricoStatusService(HistoricoStatusRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    public void registrar(Ocorrencia ocorrencia, Usuario usuario, StatusOcorrencia anterior, StatusOcorrencia novo) {
        historicoRepository.save(new HistoricoStatus(ocorrencia, usuario, anterior, novo));
    }
}
