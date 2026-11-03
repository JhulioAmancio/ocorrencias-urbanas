package com.ocorrencias.model;

public enum Categoria {
    ILUMINACAO("Iluminação"),
    BURACOS("Buracos"),
    ACESSIBILIDADE("Acessibilidade"),
    RESIDUOS("Resíduos"),
    ALAGAMENTOS("Alagamentos");

    private final String descricao;

    Categoria(String descricao) { this.descricao = descricao; }

    public String getDescricao() { return descricao; }
}
