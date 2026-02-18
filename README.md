# DevCalc API

API REST em **Spring Boot** para operações matematicas simples, usada como base para estudos de pipeline CI/CD com GitHub Actions.

## Tecnologias
- Java 17
- Spring Boot 3
- Maven
- JUnit 5
- GitHub Actions

## Estrutura
- `src/main/java/com/devcalc/`
- `src/test/java/com/devcalc/`
- `.github/workflows/`
- `evidencias/`

## Endpoints
Com a aplicacao em execucao (`http://localhost:8080`):

- `GET /add?a=10&b=5` -> `15`
- `GET /subtract?a=10&b=5` -> `5`
- `GET /multiply?a=10&b=5` -> `50`
- `GET /divide?a=10&b=5` -> `2`

## Como executar localmente
```bash
mvn clean install
mvn spring-boot:run
```

## Como testar
```bash
mvn test
```

## Workflows
- `hello.yml`: mensagem simples ao receber `push` na `main` e `pull_request`.
- `ci.yml`: pipeline com jobs `checkout`, `build`, `test`, `package` e `deploy`, com suporte a execucao manual (`workflow_dispatch`).

## Evidencias da atividade
Alteração no readme.md para mostrar que não rodará o workflow