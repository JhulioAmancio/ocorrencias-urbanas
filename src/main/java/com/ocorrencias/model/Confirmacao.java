package com.ocorrencias.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "ocorrencia_id"}))
public class Confirmacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime data;
    @ManyToOne(optional = false)
    private Usuario usuario;
    @ManyToOne(optional = false)
    private Ocorrencia ocorrencia;

    public Confirmacao() {}
    public Confirmacao(Usuario usuario, Ocorrencia ocorrencia) {
        this.usuario = usuario; this.ocorrencia = ocorrencia; this.data = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public LocalDateTime getData() { return data; }
    public Usuario getUsuario() { return usuario; }
    public Ocorrencia getOcorrencia() { return ocorrencia; }
}
