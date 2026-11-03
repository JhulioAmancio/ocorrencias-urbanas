package com.ocorrencias.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistoricoStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private StatusOcorrencia statusAnterior;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private StatusOcorrencia statusNovo;
    @Column(nullable = false)
    private LocalDateTime data;
    @ManyToOne(optional = false)
    private Ocorrencia ocorrencia;
    @ManyToOne(optional = false)
    private Usuario usuario;

    public HistoricoStatus() {}
    public HistoricoStatus(Ocorrencia o, Usuario u, StatusOcorrencia anterior, StatusOcorrencia novo) {
        this.ocorrencia = o; this.usuario = u; this.statusAnterior = anterior; this.statusNovo = novo;
        this.data = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public StatusOcorrencia getStatusAnterior() { return statusAnterior; }
    public StatusOcorrencia getStatusNovo() { return statusNovo; }
    public LocalDateTime getData() { return data; }
    public Ocorrencia getOcorrencia() { return ocorrencia; }
    public Usuario getUsuario() { return usuario; }
}
