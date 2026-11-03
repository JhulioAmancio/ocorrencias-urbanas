package com.ocorrencias.service;

import com.ocorrencias.dto.UsuarioForm;
import com.ocorrencias.exception.EmailJaCadastradoException;
import com.ocorrencias.model.Papel;
import com.ocorrencias.model.Usuario;
import com.ocorrencias.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarConta(UsuarioForm form) {
        String email = form.getEmail().trim().toLowerCase();

        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailJaCadastradoException(email);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(form.getNome().trim());
        usuario.setEmail(email);
        usuario.setSenhaHash(gerarHash(form.getSenha()));
        usuario.setPapel(Papel.CIDADAO);
        return usuarioRepository.save(usuario);
    }

    // retorna null se email ou senha estiverem errados
    public Usuario autenticar(String email, String senha) {
        return usuarioRepository.findByEmailIgnoreCase(email.trim())
                .filter(u -> encoder.matches(senha, u.getSenhaHash()))
                .orElse(null);
    }

    public String gerarHash(String senha) {
        return encoder.encode(senha);
    }
}
