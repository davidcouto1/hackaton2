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

## Testes
```bash
mvn test
```

## Endpoints
- Documentados via Swagger em `/swagger-ui.html`

## Collection Postman
Arquivo `hackaton2.json` incluso no projeto.

## Diagrama
![Diagrama](docs/diagrama.png)

## Métricas
- Disponíveis em `/actuator/metrics` para Prometheus.

## Cache
- Implementado com Caffeine para otimizar consultas e simulações.

## Circuit Breaker & Rate Limiter
- Proteção de serviços críticos com Resilience4j (circuit breaker e rate limiter).

## Cobertura de Testes
- Relatório gerado por JaCoCo em `target/site/jacoco/index.html`

## Pipeline
- Workflow GitHub Actions em `.github/workflows/ci.yml`

## Teste de carga
- Scripts K6/JMeter em `loadtest/` e resultados no README.

## Observações
- Projeto pronto para avaliação, seguindo boas práticas REST, documentação, testes e automação.

- Resiliente contra falhas e sobrecarga, com cache, circuit breaker e rate limiter.
