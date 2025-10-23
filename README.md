# Elevador - Projeto Teste Admissional Apisul

Este projeto implementa a lógica para gerar relatórios sobre o uso de 5 elevadores em um prédio com 16 andares a partir de um arquivo de entrada `input.json`.

Resumo das funcionalidades
- Identifica o(s) andar(es) menos utilizado(s).
- Identifica o(s) elevador(es) mais e menos frequentados e seus períodos de maior/menor fluxo.
- Identifica o período de maior utilização do conjunto de elevadores.
- Calcula o percentual de uso de cada elevador em relação ao total de serviços.

Arquivo de entrada:
- `src/main/resources/input/input.json`

### Como executar
1. Pré-requisitos
   - Java 17+
   - Maven


2. Construir o projeto

```bat
mvn clean package
```

3. Executar a aplicação

```bat
mvn spring-boot:run
```

A aplicação por padrão usa a porta 8080.

Swagger / OpenAPI
- Interface interativa (Swagger):
  - http://localhost:8080/swagger-ui/index.html


Principais endpoints (base: `/api`)
- GET /api/relatorio -> Retorna o relatório completo (JSON com todas as métricas)
- GET /api/andar/menos -> Lista de andares menos utilizados
- GET /api/elevador/mais -> Lista de elevadores mais frequentados
- GET /api/elevador/mais/periodos?elevador=A -> Períodos de maior fluxo (opcional: parâmetro `elevador` A..E)
- GET /api/elevador/menos -> Lista de elevadores menos frequentados
- GET /api/elevador/menos/periodos?elevador=A -> Períodos de menor fluxo (opcional)
- GET /api/periodo/mais -> Período(s) de maior utilização do conjunto
- GET /api/percentual?elevador=A -> Percentual de uso por elevador (sem param retorna para todos)

Testes
- Para executar a suíte de testes:

```bat
mvn test
```
