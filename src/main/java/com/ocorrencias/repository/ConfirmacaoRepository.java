package com.ocorrencias.repository;

import com.ocorrencias.model.Confirmacao;
import com.ocorrencias.model.Ocorrencia;
import com.ocorrencias.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmacaoRepository extends JpaRepository<Confirmacao, Long> {
    boolean existsByUsuarioAndOcorrencia(Usuario usuario, Ocorrencia ocorrencia);
}
