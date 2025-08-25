# Hackaton2 - Simulador de Crédito

## Descrição
API para simulação de crédito, consulta de produtos, persistência de simulações e telemetria. Desenvolvido em Java (Spring Boot), seguindo padrões REST e preparado para rodar em qualquer ambiente.

## Como rodar

### Requisitos
- Java 17
- Maven
- Docker (opcional)

### Rodando localmente
```powershell
mvn clean package
mvn spring-boot:repackage
java -jar target\hackaton2-1.0-SNAPSHOT.jar
```

> **Importante:**
> 1. Sempre rode `mvn clean package` antes de `mvn spring-boot:repackage` para evitar o erro "Source file must not be null".
> 2. O comando `spring-boot:repackage` garante que o JAR seja executável e evita o erro "nenhum atributo de manifesto principal".

### Rodando com Docker
```bash
docker build -t hackaton2 .
docker run -p 8080:8080 hackaton2
```


### Rodando com Docker Compose (com Load Balance)
```powershell
docker-compose up
```
O ambiente irá subir múltiplas réplicas do app e um NGINX como proxy reverso para balanceamento de carga.
O acesso à API será feito via `http://localhost:8080` (NGINX distribui as requisições entre as instâncias).

Arquivos relevantes:
- `docker-compose.yml`: define réplicas e serviços
- `nginx.conf`: configuração do balanceamento

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

> Exemplo de diagrama: Caso não visualize acima, consulte o arquivo docs/diagrama.png ou adicione um link para o diagrama da arquitetura.


## Métricas e Monitoramento
- Métricas expostas em `/actuator/metrics` e `/actuator/prometheus`.
- Monitoramento completo via Prometheus e Grafana pelo Docker Compose.

### Como usar Prometheus e Grafana
1. Execute:
	```powershell
	docker-compose up
	```
2. Acesse as interfaces:
	- Prometheus: [http://localhost:9090](http://localhost:9090)
	- Grafana: [http://localhost:3000](http://localhost:3000) (login padrão: admin/admin)
3. O Prometheus coleta métricas do app automaticamente.
4. No Grafana, adicione o Prometheus como fonte de dados (`http://prometheus:9090`) e importe dashboards para Spring Boot.

Arquivo de configuração Prometheus: `prometheus.yml` já incluso.

## Cache
- Implementado com Caffeine para otimizar consultas e simulações.

## Circuit Breaker & Rate Limiter
- Proteção de serviços críticos com Resilience4j (circuit breaker e rate limiter).

## Tratamento de Rate Limit
- Quando o limite de requisições é excedido, a API retorna HTTP 429 com mensagem amigável.

## Cobertura de Testes
- Relatório gerado por JaCoCo em `target/site/jacoco/index.html`
- Testes de integração para simulação, telemetria e relatórios implementados.

> Exemplo de cobertura:
> ![Cobertura JaCoCo](docs/jacoco-example.png)

## Pipeline
- Workflow GitHub Actions em `.github/workflows/ci.yml`

## Teste de carga
- Scripts K6/JMeter em `loadtest/` e resultados no README.

> Exemplo de resultado:
> ![Resultado K6](docs/k6-result.png)


## Observações
- Projeto pronto para avaliação, seguindo boas práticas REST, documentação, testes, automação e agora com balanceamento de carga via NGINX e múltiplas réplicas Docker.
- Resiliente contra falhas e sobrecarga, com cache, circuit breaker, rate limiter e load balance.

## Padrões de Projeto
- O cálculo das parcelas de amortização (SAC/PRICE) foi refatorado usando os padrões Strategy + Factory, facilitando extensibilidade e manutenção.

## Princípios SOLID
- O projeto segue os princípios SOLID:
	- **SRP**: Cada classe tem responsabilidade única (ex: estratégias de amortização).
	- **OCP**: Novas estratégias podem ser adicionadas sem alterar código existente (Strategy/Factory).
	- **LSP**: Estratégias podem ser substituídas sem quebrar o contrato.
	- **ISP**: Interfaces são pequenas e específicas.
	- **DIP**: Estratégias são injetadas via Spring, dependência de abstrações.
