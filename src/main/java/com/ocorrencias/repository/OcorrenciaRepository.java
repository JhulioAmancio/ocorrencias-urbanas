package com.ocorrencias.repository;

import com.ocorrencias.model.Categoria;
import com.ocorrencias.model.Ocorrencia;
import com.ocorrencias.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    Optional<Ocorrencia> findByProtocolo(String protocolo);

    List<Ocorrencia> findByUsuarioOrderByDataCriacaoDesc(Usuario usuario);

    List<Ocorrencia> findAllByOrderByDataCriacaoDesc();

    // busca ocorrencias abertas da mesma categoria dentro de um "quadrado" ao redor do ponto
    @Query("SELECT o FROM Ocorrencia o WHERE o.categoria = :categoria " +
           "AND o.status NOT IN (com.ocorrencias.model.StatusOcorrencia.RESOLVIDO, com.ocorrencias.model.StatusOcorrencia.ENCERRADO) " +
           "AND o.latitude BETWEEN :latMin AND :latMax " +
           "AND o.longitude BETWEEN :lngMin AND :lngMax")
    List<Ocorrencia> buscaSimilares(Categoria categoria, double latMin, double latMax, double lngMin, double lngMax);
}
