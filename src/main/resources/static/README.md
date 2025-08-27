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
   ```
   O arquivo JAR será criado na pasta `target` e utilizado pelo Docker. Certifique-se de executar esse comando sempre que fizer alterações no código.

2. Construa a imagem Docker:
   ```powershell
   docker build -t hackaton2 .
   ```

3. Execute o container:
   ```powershell
   docker run -p 8080:8080 hackaton2
   ```

> **Observação:** Sempre gere o JAR antes de buildar a imagem para garantir que o código está atualizado.

### Rodando com Docker Compose (ambiente completo e balanceamento de carga)
Para subir todos os serviços integrados (aplicação, banco, NGINX, Prometheus, Grafana, Zipkin):

1. Gere o JAR atualizado:
   ```powershell
   mvn clean package
   ```
   O arquivo JAR será criado na pasta `target` e utilizado pelo Docker. Certifique-se de executar esse comando sempre que fizer alterações no código.

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
