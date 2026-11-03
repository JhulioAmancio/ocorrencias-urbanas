package com.ocorrencias.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ocorrencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Categoria categoria;
    @Column(nullable = false, length = 1000)
    private String descricao;
    private String fotoUrl;
    private double latitude;
    private double longitude;
    @Column(nullable = false)
    private LocalDateTime dataCriacao;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private StatusOcorrencia status;
    @Column(nullable = false, unique = true)
    private String protocolo;

    @ManyToOne(optional = false)
    private Usuario usuario;

    @ManyToOne
    private Setor setor;

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
    @OrderBy("data ASC")
    private List<HistoricoStatus> historico = new ArrayList<>();

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
    private List<Confirmacao> confirmacoes = new ArrayList<>();

    public Long getId() { return id; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public StatusOcorrencia getStatus() { return status; }
    public void setStatus(StatusOcorrencia status) { this.status = status; }
    public String getProtocolo() { return protocolo; }
    public void setProtocolo(String protocolo) { this.protocolo = protocolo; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Setor getSetor() { return setor; }
    public void setSetor(Setor setor) { this.setor = setor; }
    public List<HistoricoStatus> getHistorico() { return historico; }
    public List<Confirmacao> getConfirmacoes() { return confirmacoes; }
}
