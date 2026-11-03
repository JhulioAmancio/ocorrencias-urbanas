package com.ocorrencias.repository;

import com.ocorrencias.model.Categoria;
import com.ocorrencias.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface SetorRepository extends JpaRepository<Setor, Long> {
    @Query("select s from Setor s where :categoria member of s.categoriaResponsavel")
    Optional<Setor> buscarPorCategoria(Categoria categoria);
}
