# Explicacao: Workflows x Actions

## 1) Diferenca entre workflow e action

- **Workflow**: e o arquivo que organiza a automacao no GitHub Actions. Ele define *quando* executar (`on:`), *quais jobs* rodar e *em que ordem* (com `needs:`).
- **Action**: e um componente reutilizavel que executa uma tarefa especifica dentro de um step, chamado via `uses:`.

Resumindo: no fluxo, o workflow coordena o processo completo; a action executa tarefas unitarias dentro desse processo.

## 2) Como uma action e estruturada internamente

Uma action normalmente possui:

- codigo da logica (JavaScript, Docker ou composite steps);
- um arquivo `action.yml` na raiz;
- metadados (nome, descricao, autor);
- declaracao de entradas (`inputs`) e saidas (`outputs`);
- definicao de execucao (`runs`) informando como a action sera executada.

## 3) Papel do arquivo `action.yml`

O `action.yml` funciona como contrato da action.

- **inputs**: parametros que o workflow pode enviar para a action.
- **outputs**: valores que a action devolve para outros steps/jobs.
- **runs**: define o mecanismo de execucao (por exemplo, `using: node20`, `using: docker` ou `using: composite`) e qual comando/entrypoint sera executado.

Sem o `action.yml`, o GitHub Actions nao sabe como a action deve ser executada.

## 4) Exemplo real no projeto (SonarQube Cloud Scan)

No workflow `ci.yml`, a action externa e chamada assim:

```yaml
- name: SonarQube Cloud Scan
  uses: SonarSource/sonarqube-scan-action@v4.2.0
  env:
    SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
  with:
    args: >
      -Dsonar.organization=${{ vars.SONAR_ORGANIZATION }}
      -Dsonar.projectKey=${{ vars.SONAR_PROJECT_KEY }}
```

Nesse exemplo:

- `uses: SonarSource/sonarqube-scan-action@v4.2.0` para referenciar a action do Marketplace com versao fixa;
- `env` para passar credenciais sensiveis via secret (`SONAR_TOKEN`);
- `with.args` para enviar parametros de configuracao da analise (organizacao e chave do projeto).

## 5) Relacao com o workflow principal

No `ci.yml`, o job `sonar_scan` depende de `build` e `lint_and_test` com `needs:`.
Assim, a analise so acontece depois da compilacao e das validacoes iniciais, mantendo a esteira de CI/CD consistente e previsivel.
