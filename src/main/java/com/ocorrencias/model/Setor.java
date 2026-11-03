package com.ocorrencias.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Setor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "setor_categoria", joinColumns = @JoinColumn(name = "setor_id"))
    @Column(name = "categoria")
    private List<Categoria> categoriaResponsavel = new ArrayList<>();

    public Setor() {}
    public Setor(String nome, List<Categoria> categorias) { this.nome = nome; this.categoriaResponsavel = new ArrayList<>(categorias); }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Categoria> getCategoriaResponsavel() { return categoriaResponsavel; }
    public void setCategoriaResponsavel(List<Categoria> c) { this.categoriaResponsavel = c; }
}
