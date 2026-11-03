# Ocorrências Urbanas

Projeto Integrador - ADS 3º semestre.

Sistema para o cidadão registrar problemas da cidade (buracos, iluminação, alagamentos...)
e para o funcionário da prefeitura acompanhar e mudar o status.

## Tecnologias
- Java 21 + Spring Boot 3
- Thymeleaf (telas)
- Spring Data JPA + H2 (banco em memória)
- BCrypt para o hash da senha

## Como rodar
```
mvn spring-boot:run
```
Acesse http://localhost:8080

Usuários de teste (senha `123456`):
- cidadao@teste.com (cidadão)
- funcionario@teste.com (funcionário)

Console do banco: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:ocorrencias`, usuário `sa`)

> O banco é em memória, então os dados somem quando a aplicação para.

## Telas
| Rota | Tela |
|------|------|
| `/cadastro` | Criar conta |
| `/login` | Login |
| `/ocorrencias` | Lista (cidadão vê as suas, funcionário vê todas) |
| `/ocorrencias/nova` | Formulário de nova ocorrência |
| `/ocorrencias/{protocolo}` | Detalhe + histórico (funcionário altera o status aqui) |

## Fluxos (conforme os diagramas de sequência)
**Criar conta:** valida o formulário → verifica se o e-mail já existe → gera o hash da senha → salva → redireciona para o login.

**Registrar ocorrência:** valida o formulário → busca ocorrências parecidas (mesma categoria, abertas, ~150 m) →
se houver, mostra a lista para o cidadão confirmar uma existente ou registrar mesmo assim →
se não houver, salva a foto, gera o protocolo, busca o setor pela categoria, salva com status RECEBIDO,
grava o histórico e redireciona para `/ocorrencias/{protocolo}`.

## Estrutura
```
src/main/java/com/ocorrencias
├── config        (login, pasta de uploads, dados iniciais)
├── controller    (UsuarioController, OcorrenciaController)
├── dto           (UsuarioForm, OcorrenciaForm)
├── exception     (EmailJaCadastradoException)
├── model         (entidades do diagrama de classes)
├── repository
└── service       (UsuarioService, OcorrenciaService, HistoricoStatusService)

src/main/resources/templates
├── fragments/layout.html
├── usuario/      (login, cadastro)
└── ocorrencias/  (lista, formulario, similares, detalhe)
```
