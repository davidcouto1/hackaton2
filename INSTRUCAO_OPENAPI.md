# Para facilitar a avaliação, gere o arquivo Swagger/OpenAPI da sua API:

1. Rode o projeto localmente.
2. Acesse: http://localhost:8080/v3/api-docs
3. Salve o conteúdo como `openapi.json` na raiz do projeto.

O avaliador pode importar esse arquivo em ferramentas como Swagger Editor, Postman ou Insomnia para visualizar e testar todos os endpoints sem precisar rodar o backend.

Exemplo de instrução para README:

---

## Documentação Swagger/OpenAPI

- A especificação completa da API está disponível em `openapi.json` na raiz do projeto.
- Para visualizar, basta importar o arquivo em [Swagger Editor](https://editor.swagger.io/) ou outra ferramenta compatível.
- O Swagger UI também está disponível em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

**Dica:** Sempre gere o arquivo após alterações nos endpoints para manter a documentação atualizada.
