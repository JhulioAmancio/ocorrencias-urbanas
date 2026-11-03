package com.ocorrencias.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioForm {
    @NotBlank(message = "Informe seu nome")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Informe seu e-mail")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Informe uma senha")
    @Size(min = 6, max = 64, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotBlank(message = "Confirme a senha")
    private String confirmacaoSenha;

    @AssertTrue(message = "As senhas não conferem")
    public boolean isSenhasIguais() {
        return senha == null || senha.equals(confirmacaoSenha);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getConfirmacaoSenha() { return confirmacaoSenha; }
    public void setConfirmacaoSenha(String c) { this.confirmacaoSenha = c; }
}
