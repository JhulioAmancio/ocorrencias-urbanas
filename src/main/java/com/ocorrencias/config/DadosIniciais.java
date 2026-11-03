package com.ocorrencias.config;

import com.ocorrencias.model.Categoria;
import com.ocorrencias.model.Papel;
import com.ocorrencias.model.Setor;
import com.ocorrencias.model.Usuario;
import com.ocorrencias.repository.SetorRepository;
import com.ocorrencias.repository.UsuarioRepository;
import com.ocorrencias.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// cadastra os setores e um funcionario de teste quando a aplicacao sobe
@Component
public class DadosIniciais implements CommandLineRunner {

    private final SetorRepository setorRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public DadosIniciais(SetorRepository setorRepository, UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.setorRepository = setorRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) {
        setorRepository.save(new Setor("Secretaria de Obras", List.of(Categoria.BURACOS, Categoria.ACESSIBILIDADE)));
        setorRepository.save(new Setor("Secretaria de Serviços Urbanos", List.of(Categoria.ILUMINACAO, Categoria.RESIDUOS)));
        setorRepository.save(new Setor("Defesa Civil", List.of(Categoria.ALAGAMENTOS)));

        Usuario funcionario = new Usuario();
        funcionario.setNome("Funcionário Teste");
        funcionario.setEmail("funcionario@teste.com");
        funcionario.setSenhaHash(usuarioService.gerarHash("123456"));
        funcionario.setPapel(Papel.FUNCIONARIO);
        usuarioRepository.save(funcionario);

        Usuario cidadao = new Usuario();
        cidadao.setNome("Cidadão Teste");
        cidadao.setEmail("cidadao@teste.com");
        cidadao.setSenhaHash(usuarioService.gerarHash("123456"));
        cidadao.setPapel(Papel.CIDADAO);
        usuarioRepository.save(cidadao);
    }
}
