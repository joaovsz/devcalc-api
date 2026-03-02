[![CI/CD Pipeline](https://github.com/joaovsz/devcalc-api/actions/workflows/ci.yml/badge.svg)](https://github.com/joaovsz/devcalc-api/actions/workflows/ci.yml)

# DevCalc API

Projeto de API REST em **Spring Boot** para operacoes matematicas simples, usada como base para estudos de pipeline CI/CD com GitHub Actions.

## Tecnologias
- Java 17
- Spring Boot 3
- Maven
- JUnit 5
- GitHub Actions
- SonarQube Cloud (SonarCloud)

## Estrutura
- `src/main/java/com/devcalc/`
- `src/test/java/com/devcalc/`
- `.github/workflows/`
- `evidencias/`

## Endpoints
Com a aplicacao em execucao (`http://localhost:8080`), os endpoints disponiveis sao:

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
- `hello.yml`: valida um fluxo simples ao receber `push` na `main` e `pull_request`.
- `lint-and-test.yml`: workflow reutilizavel com `workflow_call` para lint e testes.
- `ci.yml`: pipeline principal com build, lint/test reutilizaveis, scan SonarCloud, package e deploy simulado.

## Parametros de execucao manual
No `workflow_dispatch` do `ci.yml`, existem dois parametros booleanos:

- `run_tests`: define se os testes devem ser executados.
- `run_lint`: define se o lint com Checkstyle deve ser executado.

Em `push` e `pull_request`, ambos rodam por padrao.

## TP2 - Depuracao e Monitoramento
A falha proposital do pipeline foi identificada pela aba **Actions**, analisando a execucao com erro no nivel de **job** e depois no nivel de **step** para localizar exatamente o comando que falhou (com log detalhado e codigo de saida). Em seguida, o workflow foi corrigido em branch de feature e um novo push no mesmo PR confirmou a recuperacao da esteira, com todos os jobs em status verde.

Comparando os gatilhos: no modo **push** a execucao e automatica e dispara conforme as regras do `on.push.paths`; no modo **Run workflow** a execucao e manual e permite escolher os parametros `run_tests` e `run_lint`, tornando a validacao mais flexivel para cenarios especificos de verificacao.

