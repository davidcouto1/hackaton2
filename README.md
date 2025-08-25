# Hackaton2 - Simulador de Crédito

## Descrição
API para simulação de crédito, consulta de produtos, persistência de simulações e telemetria. Desenvolvido em Java (Spring Boot), seguindo padrões REST e preparado para rodar em qualquer ambiente.

## Como rodar

### Requisitos
- Java 17
- Maven
- Docker (opcional)

### Rodando localmente
```bash
mvn clean package spring-boot:repackage
java -jar target/hackaton2-1.0-SNAPSHOT.jar
```

### Rodando com Docker
```bash
docker build -t hackaton2 .
docker run -p 8080:8080 hackaton2
```

### Rodando com Docker Compose
```bash
docker-compose up
```

## Automação de Deploy
- Para ambientes de produção, utilize o `docker-compose.yml` para orquestrar o deploy do app e banco.
- Recomenda-se configurar variáveis de ambiente e volumes para persistência.

## Segurança
- Configure variáveis sensíveis (senhas, tokens) em um arquivo `.env` e referencie no `docker-compose.yml`.
- Nunca versionar arquivos `.env` com dados reais.

## Testes
```bash
mvn test
```

## Endpoints
- Documentados via Swagger em `/swagger-ui.html`

## Auditoria
- Endpoint `/api/auditoria` para consulta dos logs de operações (detalhes nos arquivos de log do serviço).

- Auditoria das operações registrada em `logs/hackaton2.log`.
- Endpoint documentado no Swagger, detalhando retorno e finalidade.

## Collection Postman
Arquivo `hackaton2.json` incluso no projeto.

## Diagrama
![Diagrama](docs/diagrama.png)

## Métricas
- Disponíveis em `/actuator/metrics` para Prometheus.

## Monitoramento
- Integração pronta para Prometheus (endpoint `/actuator/prometheus`).
- Recomenda-se uso do Grafana para dashboards customizados.

## Cache
- Implementado com Caffeine para otimizar consultas e simulações.

## Circuit Breaker & Rate Limiter
- Proteção de serviços críticos com Resilience4j (circuit breaker e rate limiter).

## Tratamento de Rate Limit
- Quando o limite de requisições é excedido, a API retorna HTTP 429 com mensagem amigável.

## Cobertura de Testes
- Relatório gerado por JaCoCo em `target/site/jacoco/index.html`

- Testes de integração para simulação, telemetria e relatórios implementados.

## Pipeline
- Workflow GitHub Actions em `.github/workflows/ci.yml`

## Teste de carga
- Scripts K6/JMeter em `loadtest/` e resultados no README.

## Observações
- Projeto pronto para avaliação, seguindo boas práticas REST, documentação, testes e automação.

- Resiliente contra falhas e sobrecarga, com cache, circuit breaker e rate limiter.
