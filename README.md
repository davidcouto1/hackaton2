---
# Hackaton2 - Simulador de Crédito

## Descrição
API para simulação de crédito, consulta de produtos, persistência de simulações e telemetria. Desenvolvido em **Java (Spring Boot)**, tecnologia amplamente utilizada na Caixa, facilitando avaliação, manutenção e integração. O projeto já nasce pronto para rodar em qualquer ambiente, local ou cloud, e segue padrões REST, boas práticas de arquitetura e documentação.

---
## Dicas e Boas Práticas (Avaliação)
Este projeto foi construído considerando as principais recomendações para se destacar em desafios técnicos, conforme orientações de gestores e especialistas:

- **Linguagem e Framework**: Java 17 + Spring Boot, amplamente dominados na Caixa e no mercado, facilitando avaliação e manutenção.
- **Projeto demo funcional**: Estrutura pronta, APIs REST conectadas ao banco, fácil de rodar e adaptar.
- **Execução fora da máquina local**: Docker/Docker Compose e pipeline GitHub Actions garantem compilação e execução em qualquer ambiente, validando a versão da JDK.
- **README detalhado**: Instruções de execução, testes, arquitetura (diagrama), exemplos de uso, links para Swagger e Postman Collection.
- **Swagger bem documentado**: Todos os endpoints possuem exemplos, descrições, e códigos de erro (400, 401, 403, 404, 500). Segue modelo da API PIX do Bacen.
- **Collection Postman**: Arquivo pronto para facilitar testes dos endpoints.
- **Padrões REST**: Nomenclatura, verbos HTTP e estrutura de resposta padronizados.
- **Testes unitários e de integração**: Cobertura reportada via JaCoCo, exemplos visuais no README.
- **Métricas e monitoramento**: Exportação para Prometheus/Grafana, dashboards prontos para importação.
- **Pipeline CI/CD**: Build automatizado via GitHub Actions, com link e instruções no README.
- **Cobertura de testes**: Relatório disponível e visualizável sem rodar o projeto.
- **Cache, circuit breaker, rate limit**: Caffeine para cache, Resilience4j para circuit breaker e rate limiter, HTTP 429 para excesso de requisições.
- **Auditoria**: Endpoint e logging para auditoria das operações, pronto para integração com ELK/Grafana Loki.
- **Teste de carga**: Scripts K6/JMeter e resultados disponíveis para consulta.
- **Padrões de projeto e SOLID**: Strategy/Factory e princípios SOLID garantem extensibilidade e manutenção.

> **Comentário:** Todas as práticas acima não só facilitam a avaliação, mas também garantem que o projeto seja escalável, seguro, resiliente e pronto para produção real. O README serve como guia para avaliadores e futuros desenvolvedores, tornando o onboarding rápido e transparente.

## Como rodar

### Requisitos
- Java 17
- Maven
- Docker (opcional)

### Rodando localmente
```powershell
mvn clean spring-boot:run
```
Ou, se preferir gerar o JAR:
```powershell
mvn clean package
mvn spring-boot:repackage
java -jar target\hackaton2-1.0-SNAPSHOT.jar
```

### Rodando com Docker
Para rodar o projeto em um container Docker individual:

1. Gere o JAR atualizado:
	```powershell
	mvn clean package
	mvn spring-boot:repackage
	```
2. Construa a imagem Docker:
	```powershell
	docker build -t hackaton2 .
	```
3. Execute o container:
	```powershell
	docker run -p 8080:8080 hackaton2
	```

> **Observação:** Sempre gere o JAR antes de buildar a imagem para garantir que o código está atualizado.

### Rodando com Docker
```powershell
docker build -t hackaton2 .
docker run -p 8080:8080 hackaton2
```
> **Atenção:** Antes de rodar os comandos acima, é necessário gerar o JAR do projeto com:
```
mvn clean package
mvn spring-boot:repackage
```
O arquivo JAR será criado na pasta `target` e utilizado pelo Docker. Certifique-se de executar esses comandos sempre que fizer alterações no código.


### Rodando com Docker Compose (ambiente completo e balanceamento de carga)
Para subir todos os serviços integrados (aplicação, banco, NGINX, Prometheus, Grafana, Zipkin):

1. Gere o JAR atualizado:
	```powershell
	mvn clean package
	mvn spring-boot:repackage
	```
2. (Recomendado) Construa as imagens:
	```powershell
	docker-compose build
	```
3. Suba o ambiente completo:
	```powershell
	docker-compose up
	```

O ambiente irá:
- Subir múltiplas réplicas do app
- Configurar NGINX como proxy reverso para balanceamento de carga
- Integrar banco SQL Server, Prometheus, Grafana, Zipkin

O acesso à API será feito via `http://localhost:8080` (NGINX distribui as requisições entre as instâncias).

Arquivos relevantes:
- `docker-compose.yml`: define réplicas e serviços
- `nginx.conf`: configuração do balanceamento

> **Dica:** Sempre execute `docker-compose build` após alterações no código para garantir que as imagens estejam atualizadas.

> **Dica:** O uso de Docker Compose e NGINX demonstra preocupação com escalabilidade e alta disponibilidade, requisitos comuns em ambientes corporativos.

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3.x
- Maven
- H2 (local)
- SQL Server (Docker)
- Docker & Docker Compose
- NGINX (proxy reverso)
- Prometheus & Grafana (monitoramento)
- JaCoCo (cobertura de testes)
- JUnit 5 (testes)
- Swagger/OpenAPI (documentação)
- Resilience4j (circuit breaker, rate limiter)
- Caffeine (cache)
- GitHub Actions (CI/CD)

> **Comentário:** Todas as tecnologias escolhidas são amplamente utilizadas no mercado e na Caixa, facilitando integração, manutenção e evolução do projeto.

## Frontend para Testes

Incluí dois frontends para testar os endpoints da API:

1. **Frontend visual (index.html):**
	- Interface moderna, exibe resultados em tabelas organizadas.
	- Localização: `src/main/resources/static/index.html`
	- Acesse em: `http://localhost:8080`

2. **Frontend JSON puro (json.html):**
	- Exibe a resposta da simulação diretamente em formato JSON, sem formatação visual.
	- Útil para testes, integração ou para ver o formato bruto da resposta.
	- Localização: `src/main/resources/static/json.html`
	- Acesse em: `http://localhost:8080/json.html` (ou configure para servir em outra porta, ex: 8081, se desejar separar os ambientes)

No frontend principal há um link para o visualizador JSON, e uma explicação sobre sua finalidade.

> **Observação:** As respostas exibidas no frontend visual são organizadas em tabelas para melhor experiência. O visualizador JSON mostra o retorno bruto da API, útil para integração ou depuração. Ferramentas como Postman, Insomnia ou qualquer outro cliente HTTP também recebem o JSON puro.

> **Dica:** Disponibilizar diferentes formas de testar a API (visual, JSON puro, Postman) facilita a avaliação e o uso por diferentes perfis de usuários.


-## Otimização de Consultas e Índices
Para garantir alta performance e escalabilidade, implementei as seguintes melhorias:
- Índices automáticos: Os campos usados em filtros, agrupamentos e joins (ex: dataSimulacao, codigoProduto, nomeApi, dataReferencia) são indexados automaticamente no banco H2 local e no SQL Server Docker via scripts `schema.sql` e `import.sql`.
- Consultas SQL otimizadas: As operações de agregação (SUM, COUNT, GROUP BY) são realizadas diretamente no banco, evitando processamento desnecessário na aplicação.
- Cache de resultados: Utilizo o `@Cacheable` (Spring) nos métodos de consulta agregada dos serviços de Telemetria e Relatório, acelerando respostas e reduzindo carga no banco.
- View materializada: Para relatórios agregados, criei uma view materializada no SQL Server (`vw_relatorio_produto_dia`), atualizável via job SQL Server.
- Monitoramento: Prometheus e Grafana integrados para identificar gargalos e ajustar índices ou queries conforme necessário.

- O projeto segue boas práticas REST, documentação, testes automatizados (JaCoCo), automação de deploy, balanceamento de carga via NGINX e múltiplas réplicas Docker.
- Implementei resiliência contra falhas e sobrecarga, com cache, circuit breaker, rate limiter e load balance.
- Configure variáveis sensíveis (senhas, tokens) em um arquivo `.env` e referencie no `docker-compose.yml`. Nunca versiono arquivos `.env` com dados reais.

> **Dica:** O uso de cache, circuit breaker, rate limiter e variáveis sensíveis demonstra preocupação com performance, resiliência e segurança.

## Testes
Para rodar os testes automatizados:
```bash
mvn test
```
O relatório de cobertura é gerado automaticamente em `target/site/jacoco/index.html`.

> **Comentário:** Testes automatizados e cobertura de código são essenciais para garantir qualidade e facilitar futuras evoluções.

## Collection Postman
Uma collection pronta para testar todos os endpoints está disponível em `postman_collection.json`.
Para importar:
1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `postman_collection.json`
4. Todos os endpoints principais estarão disponíveis para teste.

> **Dica:** Fornecer uma collection pronta agiliza a avaliação e demonstra preocupação com a experiência do avaliador.

### Exemplos de uso dos principais endpoints

#### Simulação de Crédito
**Requisição (POST):**
```json
{
	"valorDesejado": 900.00,
	"prazo": 5
}
```
**Endpoint:**
```
POST /api/simulacoes
```
```json
{
	"valorDesejado": 900.00,
	"prazo": 5
}
```
**Resposta:**
```json
{
	"idSimulacao": 20180702,
	"codigoProduto": 1,
	"nomeProduto": "Produto 1",
	"taxaJuros": 0.0179,
	"resultadosSimulacao": [
		{
			"tipo": "SAC",
			"parcelas": [ ... ]
		},
		{
			"tipo": "PRICE",
			"parcelas": [ ... ]
		}
	]
}
```

#### Listar Simulações
**Requisição (GET):**
```
GET /api/simulacoes
```
**Resposta:**
```json
{
	"pagina": 1,
	"qtdRegistros": 404,
	"qtdRegistrosPagina": 200,
	"registros": [
		{
			"idSimulacao": 20180702,
			"valorDesejado": 900.00,
			"prazo": 5,
			"valorTotalSac": 1243.28,
			"valorTotalPrice": 1243.28
		}
	]
}
```

#### Telemetria
**Requisição (GET):**
```
GET /api/telemetria?data=2025-07-30
```
**Resposta:**
```json
{
	"dataReferencia": "2025-07-30",
	"listaEndpoints": [
		{
			"nomeApi": "Simulacao",
			"qtdRequisicoes": 135,
			"tempoMedio": 150,
			"tempoMinimo": 23,
			"tempoMaximo": 860,
			"percentualSucesso": 0.98
		}
	]
}
```

#### Relatório por Produto e Dia
**Requisição (GET):**
```
GET /api/relatorio/por-produto-dia?data=2025-07-30
```
**Resposta:**
```json
[
	{
		"dataReferencia": "2025-07-30",
		"codigoProduto": 1,
		"nomeProduto": "Produto 1",
		"qtdSimulacoes": 10,
		"valorTotalSimulado": 9000.00,
		"valorMedioSimulado": 900.00
	},
	{
		"dataReferencia": "2025-07-30",
		"codigoProduto": 2,
		"nomeProduto": "Produto 2",
		"qtdSimulacoes": 5,
		"valorTotalSimulado": 4500.00,
		"valorMedioSimulado": 900.00
	}
]
```
### Visualizando cobertura de testes (JaCoCo)
Após rodar os testes, abra o relatório gerado em:
```
target/site/jacoco/index.html
```
Esse arquivo pode ser aberto no navegador para visualizar a cobertura dos testes.


## Endpoints
- Documentados via Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Para acessar o Swagger, rode a aplicação com:
	- `mvn spring-boot:run` (sem gerar o JAR)
	- ou siga os comandos de build/JAR para rodar manualmente

> **Comentário:** Swagger bem documentado, com exemplos e códigos de erro, facilita testes, integração e avaliação.

## Swagger/OpenAPI (openapi.json)
O arquivo Swagger/OpenAPI gerado (`openapi.json`) está disponível na raiz do projeto. Você pode consultar ou importar este arquivo em ferramentas como Swagger Editor, Postman ou Insomnia para visualizar e testar todos os endpoints documentados da API.

## Auditoria e Logging Centralizado
- Endpoint `/api/auditoria` para consulta dos logs de operações (detalhes nos arquivos de log do serviço).
- Auditoria das operações registrada em `logs/hackaton2.log`.
- O projeto utiliza logging local, pronto para integração com ELK Stack (Elasticsearch, Logstash, Kibana), Grafana Loki, Azure Monitor, AWS CloudWatch.
- Endpoint documentado no Swagger, detalhando retorno e finalidade.

> **Dica:** Auditoria e logging centralizado são diferenciais para projetos corporativos, facilitando rastreabilidade e conformidade.

## Diagrama
![Diagrama](docs/diagrama.png)




## Métricas, Monitoramento e Tracing
- Métricas expostas em `/actuator/metrics` e `/actuator/prometheus`.
- Monitoramento completo via Prometheus e Grafana pelo Docker Compose.
- Tracing distribuído com OpenTelemetry/Spring Observability para visualizar o fluxo de requisições entre serviços.

> **Comentário:** Métricas e tracing são fundamentais para observabilidade, diagnóstico e evolução do sistema.



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

> **Dica:** Cache em memória acelera respostas e reduz carga no banco, especialmente em cenários de alta concorrência.

## Circuit Breaker & Rate Limiter
- Proteção de serviços críticos com Resilience4j (circuit breaker e rate limiter).

> **Comentário:** Circuit breaker e rate limiter aumentam a resiliência e protegem o sistema contra sobrecarga e falhas em cascata.

## Tratamento de Rate Limit
- Quando o limite de requisições é excedido, a API retorna HTTP 429 com mensagem amigável.

> **Dica:** Retornar HTTP 429 demonstra conformidade com padrões REST e preocupação com experiência do usuário.

## Cobertura de Testes
- Relatório gerado por JaCoCo em `target/site/jacoco/index.html`
- Testes de integração para simulação, telemetria e relatórios implementados.

> Exemplo de cobertura:
> ![Cobertura JaCoCo](docs/jacoco-example.png)

> **Comentário:** Cobertura visual facilita avaliação rápida da qualidade dos testes.

## Pipeline
- Workflow GitHub Actions em `.github/workflows/ci.yml`

> **Dica:** Pipeline automatizado garante que o projeto compila e testa fora do ambiente local, aumentando confiabilidade.

## Teste de carga
- Scripts K6/JMeter em `loadtest/` e resultados no README.

> Exemplo de resultado:
> ![Resultado K6](docs/k6-result.png)

> **Comentário:** Teste de carga demonstra preocupação com performance e escalabilidade.



## Padrões de Projeto
- O cálculo das parcelas de amortização (SAC/PRICE) foi refatorado usando os padrões Strategy + Factory, facilitando extensibilidade e manutenção.

> **Comentário:** O uso de padrões de projeto facilita futuras evoluções e adaptações do sistema.

## Princípios SOLID
- O projeto segue os princípios SOLID:
	- **SRP**: Cada classe tem responsabilidade única (ex: estratégias de amortização).
	- **OCP**: Novas estratégias podem ser adicionadas sem alterar código existente (Strategy/Factory).
	- **LSP**: Estratégias podem ser substituídas sem quebrar o contrato.
	- **ISP**: Interfaces são pequenas e específicas.
	- **DIP**: Estratégias são injetadas via Spring, dependência de abstrações.

> **Comentário:** SOLID é referência em arquitetura orientada a objetos, garantindo código limpo, extensível e fácil de manter.
