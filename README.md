# Sistema de Gerenciamento de Usuários e Perfis

## 1. Introdução

### 1.1 Visão Geral
Este é um sistema de gerenciamento de usuários e perfis, onde usuários podem ser cadastrados e associados a um perfil.

### 1.2 Objetivos
O objetivo principal é demonstrar como criar um sistema com um backend Spring Boot e um frontend Angular, com autenticação e autorização.

### 1.3 Funcionalidades
*   Tela de login para usuários com `httpBasic`.
*   Criação automática de um usuário administrador inicial ("admin@example.com" / "admin").
*   Criação, edição e listagem de usuários (apenas para usuários com role `ADMINISTRADOR`).
*   Criação, edição e listagem de perfis (apenas para usuários com role `ADMINISTRADOR`).
* Listar usuários e perfis (para todos os usuários autenticados).
*   Autenticação via `httpBasic`.

### 1.4 Público-Alvo
Desenvolvedores que precisam aprender ou implementar um sistema de gerenciamento de usuários com autenticação e autorização utilizando Angular e Spring Boot.

## 2. Arquitetura do Sistema

### 2.1 Visão Geral
O sistema é dividido em duas partes principais: um backend (em Java com Spring Boot) que gerencia a lógica de negócios e a persistência de dados, e um frontend (em Angular) que fornece a interface do usuário.

### 2.2 Tecnologias
*   **Backend:** Java 17+, Spring Boot, Spring Data JPA, H2 Database (em memória), Spring Security, JWT (JSON Web Token).
*   **Frontend:** Angular 16+, TypeScript, HTML, CSS, Angular Material.

### 2.3 Diagrama (Opcional)
*(Aqui você pode adicionar um diagrama de arquitetura, caso deseje)*

## 3. Backend (Java com Spring Boot)

### 3.1 Endpoints da API
*   `POST /auth/login`: Realiza a autenticação do usuário e retorna o token JWT.
    *   **Corpo da Requisição:** Username e password (em JSON).
    *   **Headers:** `Content-Type: application/json`.
    *   **Resposta de Sucesso:** Status `200` e um objeto JSON com o `token` .
    *   **Resposta de Erro:** Status `401` (Unauthorized).
*  `GET /auth/me`: Retorna as informações do profile do usuário logado.
    *  **Headers:** `Authorization: Basic <base64>`
    *   **Resposta de Sucesso:** Status `200` e um objeto com as informações do profile do usuário.
    *   **Resposta de Erro:** Status `401` (Unauthorized).
*   `GET /users`: Retorna todos os usuários.
    *   **Headers:** `Authorization: Bearer <token>`
    *   **Resposta de Sucesso:** Status `200` e uma lista de objetos com informações do usuário.
    *   **Resposta de Erro:** Status `401` (Unauthorized).
*   `GET /users/{id}`: Retorna um usuário específico pelo id.
    *   **Headers:** `Authorization: Bearer <token>`
    *   **Resposta de Sucesso:** Status `200` e um objeto com informações do usuário.
    *   **Resposta de Erro:** Status `404` (Not Found), `401` (Unauthorized).
*   `POST /users`: Cria um novo usuário (apenas para administradores).
     *  **Headers:** `Authorization: Bearer <token>`, `Content-Type: application/json`
    *   **Corpo da Requisição:** JSON com as informações do usuário.
    *   **Resposta de Sucesso:** Status `201` (Created).
    *   **Resposta de Erro:** Status `401` (Unauthorized).
*   `PUT /users/{id}`: Atualiza um usuário específico (apenas para administradores).
    *   **Headers:** `Authorization: Bearer <token>`, `Content-Type: application/json`.
    *   **Corpo da Requisição:** JSON com as informações do usuário.
    *   **Resposta de Sucesso:** Status `200` (OK).
    *   **Resposta de Erro:** Status `404` (Not Found), `401` (Unauthorized).
*   `GET /profiles/list`: Retorna todos os perfis.
    *   **Headers:** `Authorization: Bearer <token>`
    *   **Resposta de Sucesso:** Status `200` e uma lista de objetos com informações do perfil.
     *   **Resposta de Erro:** Status `401` (Unauthorized).
*   `GET /profiles/{id}`: Retorna um perfil específico pelo id.
    *    **Headers:** `Authorization: Bearer <token>`
    *   **Resposta de Sucesso:** Status `200` e um objeto com informações do perfil.
     *    **Resposta de Erro:** Status `404` (Not Found), `401` (Unauthorized).
*   `POST /profiles`: Cria um novo perfil (apenas para administradores).
   *  **Headers:** `Authorization: Bearer <token>`, `Content-Type: application/json`.
   *   **Corpo da Requisição:** JSON com as informações do perfil.
    *   **Resposta de Sucesso:** Status `201` (Created).
    *    **Resposta de Erro:** Status `401` (Unauthorized).
*   `PUT /profiles/{id}`: Atualiza um perfil específico (apenas para administradores).
     *  **Headers:** `Authorization: Bearer <token>`, `Content-Type: application/json`.
     *  **Corpo da Requisição:** JSON com as informações do perfil.
     *  **Resposta de Sucesso:** Status `200` (OK).
    *   **Resposta de Erro:** Status `404` (Not Found), `401` (Unauthorized).

### 3.2 Configuração de Segurança
* Utiliza autenticação `httpBasic`.
* A autenticação é feita utilizando um `JpaUserDetailsService` para buscar os usuários no banco de dados.
* O backend utiliza o `DaoAuthenticationProvider` para utilizar o `JpaUserDetailsService`.
* O CORS está configurado para permitir requisições de qualquer origem.
* O usuário "admin@example.com" com a senha "admin" (encriptada) e perfil "ADMINISTRADOR" é criado automaticamente ao iniciar a aplicação.

### 3.3 Modelo de Dados (Entidades)
*   `User`: Representa um usuário do sistema.
    *   Atributos: `id`, `name`, `email`, `password`, `profile`.
*   `Profile`: Representa um perfil de usuário.
    *   Atributos: `id`, `name`.

### 3.4 Serviços Principais
*   `JpaUserDetailsService`: Implementa o `UserDetailsService` para buscar usuários no banco de dados.
*   `InitialDataLoader`: Cria o usuário `admin` na inicialização.

### 3.5 Configuração do Banco de Dados
* Utiliza banco de dados H2 em memória.

## 4. Frontend (Angular)

### 4.1 Estrutura do Projeto
* A aplicação é criada usando o Angular CLI e utiliza componentes standalone.
* Os serviços (`auth.service`, `user.service`, `profile.service`) são responsáveis pela comunicação com o backend.
* Os componentes são divididos em pastas separadas (`user-form`, `user-list`, etc.).

### 4.2 Componentes Principais
* `AppComponent`: Componente principal que gerencia a autenticação e a exibição da tela de login ou do menu principal.
*  `LoginComponent`: Componente responsável pela tela de login.
*   `MenuComponent`: Componente que cria o menu de navegação da aplicação.
*   `UserFormComponent`: Componente para criar e editar usuários (apenas para usuários `ADMIN`).
*   `UserListComponent`: Componente para listar usuários (visível para todos os usuários autenticados).
*   `ProfileFormComponent`: Componente para criar e editar perfis (apenas para usuários `ADMIN`).
*   `ProfileListComponent`: Componente para listar perfis (visível para todos os usuários autenticados).

### 4.3 Serviços Principais
*   `AuthService`: Responsável pela autenticação do usuário e pelo tratamento do token.
*  `UserService`: Responsável pela comunicação com o backend para gerenciar os usuários.
*  `ProfileService`: Responsável pela comunicação com o backend para gerenciar os perfis.

### 4.4 Login e Logout
*   **Login:** Para fazer o login, o usuário deve inserir um email e senha na tela de login, enviando uma requisição para o endpoint `/auth/me`.
*   **Logout:** Para fazer o logout, o token e a role são removidos do `localStorage` e o usuário é redirecionado para a tela de login.

### 4.5 Gerenciamento de Usuários e Perfis
*   Apenas usuários com role `ADMIN` podem utilizar os botões para cadastrar e editar usuários e perfis.
*   Todos os usuários autenticados podem listar usuários e perfis.

## 5. Instalação e Execução

### 5.1 Configuração do Ambiente de Desenvolvimento
*   Instale o Java JDK 17+.
*   Instale o Node.js 16+ e npm.
*   Instale o Angular CLI: `npm install -g @angular/cli`.
*   Instale o Postman ou outro cliente para requisições HTTP.

### 5.2 Execução do Backend
*   Importe o projeto Spring Boot na sua IDE (IntelliJ IDEA, Eclipse, etc.).
*   Execute a classe principal do Spring Boot para iniciar o backend.
*   O backend deve estar rodando na porta 8080 ou 8084.

### 5.3 Execução do Frontend
*   Abra o terminal na pasta do seu projeto Angular.
*   Execute `ng serve` para iniciar o frontend.
*   A aplicação deverá ser aberta no seu navegador em `http://localhost:4200`.

## 6. Autenticação e Autorização

### 6.1 Sistema de Login
* O sistema utiliza autenticação `httpBasic`.
* Ao fazer login, o frontend envia o email e senha para o backend no endpoint `/auth/me`.
* O backend responde com o status 200 ou 401 e o profile do usuário no corpo da requisição.

### 6.2 Usuários
* O usuário `admin@example.com` com senha `admin` e perfil `ADMINISTRADOR` é criado ao iniciar a aplicação.

### 6.3 Autorização
* Os endpoints `/users` e `/profiles` (para criação e edição) são protegidos e só são acessíveis para usuários com a role `ADMINISTRADOR`.
* Todos os outros endpoints são acessíveis para usuários autenticados, para visualização de dados.

## 7. Implantação (Opcional)

### 7.1 Backend
Crie um JAR do seu projeto Spring Boot e hospede em um servidor.

### 7.2 Frontend
Faça o build do seu projeto angular e disponibilize os arquivos em um servidor web.

## 8. Próximos Passos e Melhorias
*   **Autenticação JWT:** Implementar um sistema de autenticação JWT com refresh tokens para maior segurança e desempenho.
*   **Validação:** Validar os campos de formulários tanto no frontend quanto no backend.
*  **Tratamento de Erros:** Implementar um sistema de tratamento de erros mais robusto no backend e no frontend.
*   **Testes:** Criar testes unitários e de integração para garantir a qualidade da aplicação.
*  **Documentação:** Documentar os componentes, serviços e rotas da aplicação.
* **Implementar edição do perfil do usuario:**
   * Implementar a funcionalidade para que o usuario possa editar suas proprias informações.

## 9. Solução de Problemas (Troubleshooting)

*   **Erro de CORS:**
    *   Verifique se a anotação `@CrossOrigin` está configurada corretamente no backend.
*   **Erro de Autenticação:**
    *   Verifique os logs do frontend e backend, as credenciais de login e o token utilizado nas requisições.
*  **Erro de Redirecionamento:**
    *   Verifique se o `ngOnInit` do seu componente está sendo chamado corretamente após o login, para pegar as informações do usuário logado.
    *  Se a tela do menu não aparece, verifique se o atributo `isLoggedIn` está setado corretamente como `true` em caso de sucesso no login.
