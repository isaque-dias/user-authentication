# Spring Authentication API

Uma API RESTful para gerenciamento de usuários com autenticação básica usando Spring Boot e Spring Security. Permite registro, atualização, visualização e exclusão de perfis de usuários, garantindo segurança através de senhas criptografadas e gerenciamento de roles.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- Validation
- FlyWay Migration

## Funcionalidades

- Registo de usuários com validação de email e username únicos
- Atualização de perfil do usuário autenticado
- Visualização do perfil do usuário autenticado
- Exclusão do perfil do usuário autenticado
- Gerenciamento de roles para autorização (ROLE_USER)
- Senhas armazenadas de forma segura com BCryptPasswordEncoder
- Tratamento dee exceções personalizadas (EmailAlreadyExistsException, UsernameAlreadyExistsException, RoleNotFoundException)


## Endpoints

Registro de usuário
```
POST /api/users/register
```

Request Body (JSON):
```
{
  "name": "João da Silva",
  "email": "joao@example.com",
  "username": "joaosilva",
  "password": "senha123"
}
```

Resposta (201 CREATED):
```
{
  "id": 1,
  "name": "João da Silva",
  "email": "joao@example.com",
  "username": "joaosilva"
}
```
Obter perfil do usuário autenticado
```
GET /api/user
```

Resposta (200 OK):
```
{
  "id": 1,
  "name": "João da Silva",
  "email": "joao@example.com",
  "username": "joaosilva"
}
```

Atualizar perfil do usuário autenticado
```
PUT /api/users
```

Request Body (JSON):
```
{
  "name": "João Silva Atualizado",
  "email": "joao@example.com",
  "username": "joaosilva",
  "password": "novasenha123"
}
```
Resposta (200 OK):
```
{
  "id": 1,
  "name": "João Silva Atualizado",
  "email": "joao@example.com",
  "username": "joaosilva"
}
```

Excluir perfil do usuário autenticado
```
DELETE /api/users
```

## Estrutura do Projeto

```
src/main/java/com/authentication/spring_authentication
├── config
│   ├── SecurityConfig.java
│   └── SecurityUtils.java
├── controllers
│   └── UserController.java
├── dto
│   ├── request/UserRequest.java
│   └── response/UserResponse.java
├── exceptions
│   ├── ControllerAdvices.java
│   ├── EmailAlreadyExistsException.java
│   ├── RoleNotFoundException.java
│   └── UsernameAlreadyExistsException.java
├── model
│   ├── Role.java
│   └── User.java
├── repositories
│   ├── RoleRepository.java
│   └── UserRepository.java
├── security
│   ├── UserDetailsImpl.java
│   └── UserDetailsServiceImpl.java
└── services
    └── UserService.java
```

## Como executar

1. Clonar o repositório
```
git clone https://github.com/seu-usuario/spring-authentication.git
cd spring-authentication
```

2. Configurar banco de dados (application.properties)
```
spring.datasource.url=jdbc:postgresql://localhost:5432/db_name
spring.datasource.username=db_user
spring.datasource.password=db_password
spring.jpa.hibernate.ddl-auto=update
```

3. Rodar a aplicação (Linux)
```
./mvnw spring-boot:run
```

Windows
```
mvnw.cmd spring-boot:run
```

4. Testar endpoints
- Use ferramentas como Postman ou Insomnia
- Para endpoints protegidos, utilize Basic Auth com username e password do usuário cadastrado


## Exceções tratadas

- EmailAlreadyExistsException -> Indicar que já existe um email cadastrado. Retorna código 409 CONFLICT.
- UsernameAlreadyExistsException -> Indicar que já existe um username cadastrado. Retorna código 409 CONFLICT.
- RoleNotFoundException -> Indicar que não foi encontrado ROLE no banco de dados. Retorna código 404 NOT FOUND.

## Próximos passos

- Adicionar autenticação via JWT (Token)
- Criar roles adicionais (ADMIN, MODERATOR) para diferentes níveis de acesso
- Implementar testes automatizados (unitários e integração)
