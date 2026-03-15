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

## TP3 - Segurança, Ambientes e Runners Customizados no CI/CD

1. **Runner Auto-Hospedado:** Configurei um runner na minha própria máquina (Mac ARM64) para rodar os jobs do GitHub Actions localmente, instalando dependências dinamicamente durante o processo.

2. **Variáveis e Secrets:** Tirei a exposição de dados fixos do código. Configurei variáveis de ambiente (`APP_MODE`, `SUPPORT_EMAIL`) e protegi dados confidenciais usando os Secrets do GitHub (`PROD_TOKEN`).

3. **Escopos e Contextos:** Organizei variáveis para mostrar a hierarquia entre níveis de workflow, job e step. Também utilizei contextos nativos (`github.actor` e `runner.os`) para melhorar o rastreio da execução.

4. **Permissões do GITHUB_TOKEN:** Ajustei as permissões do repositório e automatizei a criação de issues. Se um deploy falhar, o próprio bot do GitHub Actions abre um ticket com o erro.

5. **Ambientes (Dev e Prod):** Separei a infraestrutura lógica criando os ambientes de `dev` e `prod`. Coloquei uma trava de segurança em produção que exige a minha aprovação manual (*Required reviewers*) antes de publicar.

6. **Nova Funcionalidade na API:** Criei o endpoint `GET /sqrt?x={valor}` no Java para calcular raízes quadradas. Fiz os testes unitários (incluindo tratamento de números negativos) e integrei tudo para o pipeline validar a nova build automaticamente.

### 📄 Evidências

Toda a documentação visual com os prints de execução de cada etapa está detalhada no arquivo [README.md](./evidencias/TP3_Evidencias/README.md).

### 🚀 Como reexecutar os workflows

Deixei os workflows configurados com o gatilho `workflow_dispatch` para facilitar a correção. Para rodar novamente:

1. Vá até a aba **Actions** aqui no repositório.

2. No menu lateral esquerdo, escolha o workflow que deseja testar (ex: "Etapa 3 - Escopos e Contextos").

3. Clique no botão **Run workflow** no lado direito, selecione a branch `main` e confirme.