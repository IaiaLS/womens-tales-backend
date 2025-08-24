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
