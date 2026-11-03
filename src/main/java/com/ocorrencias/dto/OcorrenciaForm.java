package com.ocorrencias.dto;

import com.ocorrencias.model.Categoria;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class OcorrenciaForm {
    @NotNull(message = "Selecione uma categoria")
    private Categoria categoria;

    @NotBlank(message = "Descreva o problema")
    @Size(min = 10, max = 1000, message = "A descrição deve ter entre 10 e 1000 caracteres")
    private String descricao;

    @NotNull(message = "Informe a latitude")
    private Double latitude;

    @NotNull(message = "Informe a longitude")
    private Double longitude;

    private MultipartFile foto;

    // usado quando o cidadao ja viu os similares e quer registrar mesmo assim
    private boolean ignorarSimilares;

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public MultipartFile getFoto() { return foto; }
    public void setFoto(MultipartFile foto) { this.foto = foto; }
    public boolean isIgnorarSimilares() { return ignorarSimilares; }
    public void setIgnorarSimilares(boolean ignorarSimilares) { this.ignorarSimilares = ignorarSimilares; }
}
