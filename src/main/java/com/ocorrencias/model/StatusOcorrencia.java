package com.ocorrencias.model;

public enum StatusOcorrencia {
    RECEBIDO("Recebido"),
    EM_ANALISE("Em análise"),
    ENCAMINHADO("Encaminhado"),
    EM_ATENDIMENTO("Em atendimento"),
    RESOLVIDO("Resolvido"),
    ENCERRADO("Encerrado");

    private final String descricao;

    StatusOcorrencia(String descricao) { this.descricao = descricao; }

    public String getDescricao() { return descricao; }
}
