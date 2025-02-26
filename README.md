# Projeto Lang Cursos - Backend

Este é o backend da aplicação Lang Cursos, desenvolvido com Spring Boot para gerenciar cursos, alunos e professores.

## Tecnologias

* **Spring Boot:** Framework Java para desenvolvimento rápido de aplicações web.
* **Spring Data JPA:** Para persistência de dados com JPA/Hibernate.
* **Supabase:** Banco de dados PostgreSQL como serviço (BaaS).
* **Spring Security:** Para segurança da API.
* **JWT (JSON Web Tokens):** Para autenticação e autorização.
* **SpringDoc OpenAPI (Swagger UI):** Para documentação da API.
* **Maven:** Para gerenciamento de dependências e construção do projeto.

## Dependências

* `spring-boot-starter-data-jpa`
* `spring-boot-starter-web`
* `spring-boot-starter-security`
* `springdoc-openapi-starter-webmvc-ui`
* `postgresql`
* `jjwt-api`
* `jjwt-impl`
* `jjwt-jackson`
* `lombok`
* `spring-boot-devtools` (para desenvolvimento)

## Endpoints

A documentação dos endpoints da API pode ser acessada através do Swagger UI em `http://localhost:8080/swagger-ui/index.html` após a execução da aplicação.

## Configuração

1.  **Clone o repositório:**

    ```bash
    git clone <URL_DO_SEU_REPOSITÓRIO>
    ```

2.  **Navegue até o diretório do projeto:**

    ```bash
    cd Lang-Cursos-BackEnd
    ```

3.  **Configure o banco de dados Supabase:**

    * Crie uma conta no Supabase: [https://supabase.com/](https://supabase.com/)
    * Crie um novo projeto no Supabase.
    * Obtenha a URL de conexão do banco de dados no painel do Supabase.
    * Altere as configurações do banco de dados no arquivo `src/main/resources/application.properties` ou `application.yml` usando a URL do Supabase.

4.  **Execute a aplicação:**

    ```bash
    ./mvnw spring-boot:run
    ```
