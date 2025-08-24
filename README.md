# 📖 Women's Tales - Backend

API desenvolvida como parte do **Projeto Integrador - ODS 05 (Igualdade de Gênero)**.  
O objetivo é fornecer uma plataforma para compartilhamento de histórias e conteúdos sobre temas relacionados às mulheres, ciência e igualdade.

---

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3**
    - Spring Web
    - Spring Data JPA
    - Spring Security (JWT)
- **MySQL** (produção)
- **H2 Database** (testes)
- **MapStruct** (mapeamento DTO ↔ Entity)
- **Lombok**
- **Swagger / OpenAPI 3**

---

## 📌 Funcionalidades (MVP)

- **Usuários**
    - Cadastro de novo usuário
    - Autenticação via **JWT**
    - Atualização do próprio perfil
    - Listagem restrita a **admin**

- **Temas**
    - CRUD de temas
    - Bloqueio de exclusão caso haja postagens relacionadas

- **Postagens**
    - CRUD de postagens
    - Cada postagem está vinculada a um **usuário** e a um **tema**
    - Apenas o autor pode editar/deletar sua própria postagem

---

## 🔐 Autenticação

- Autenticação via **JWT**.
- Endpoints públicos:
    - `POST /auth/login`
    - `POST /usuarios/cadastrar`
- Todos os outros endpoints requerem **token Bearer** no header:

```http
Authorization: Bearer seu_token_jwt_aqui

```

📑 Documentação da API

A documentação está disponível via Swagger após subir a aplicação:

👉 http://localhost:8080/swagger-ui.html

Funcionalidades do Swagger:

Visualização dos endpoints

Exemplos de requisição e resposta

Botão Authorize para inserir o token JWT

🛠️ Como rodar o projeto
Pré-requisitos

Java 17+

Maven

MySQL (ou Docker com MySQL rodando)

Passos

Clone este repositório:

git clone https://github.com/IaiaLS/womens-tales-backend.git


Configure o banco no arquivo application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/womenstales
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha


Rode a aplicação:

mvn spring-boot:run

✅ Testes

Testes unitários para services

Testes de integração para controllers

Banco de testes usa H2 em memória, resetado a cada execução.

Rodar os testes:

mvn test

📂 Estrutura do projeto
src/main/java/com/example/womensTales
├── configuration   # Configurações (Swagger, Security, etc)
├── controller      # Controllers REST
├── dto             # Data Transfer Objects
├── entity          # Entidades JPA
├── mapper          # MapStruct Mappers
├── repository      # Interfaces JPA Repository
├── security        # JWT + Spring Security
└── service         # Regras de negócio

🤝 Contribuidores

Grupo 06 - Generation Brasil

Contato: womenstales.generation@gmail.com

GitHub: Repositório Oficial

📜 Licença

Projeto sob licença MIT
.


---

Esse modelo cobre tudo o que vocês já fizeram no **MVP**: autenticação JWT, usuários, postagens, temas, testes e Swagger.  

👉 Quer que eu adapte esse README para já incluir também **passos de deploy gratuito** (ex: Railway / Re