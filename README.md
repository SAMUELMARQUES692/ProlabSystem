# ProlabSystem

API de gestão de resíduos que registra e controla toda a entrada de resíduos em um pátio/depósito, gerencia posições de estoque e organiza o recebimento de cargas por meio de um sistema de agendamentos.

Este é o **serviço de domínio (core)** de um ecossistema de microsserviços. Ele expõe a regra de negócio e atua como **OAuth2 Resource Server**: não emite tokens, apenas valida os JWTs assinados pelo `auth-service` usando uma chave pública RSA.

## Arquitetura

O sistema é dividido em três serviços independentes:

| Serviço | Papel | Repositório |
|---|---|---|
| **auth-service** | Emite o JWT (assina com chave privada RSA), gerencia usuários e scopes, publica eventos de usuário no RabbitMQ | https://github.com/SAMUELMARQUES692/auth-service |
| **ProlabSystem** (este) | API de domínio (clientes, caminhões, agendamentos, recebimentos, resíduos, estoque). Valida o JWT com a chave pública | — |
| **message-service** | Consome os eventos do RabbitMQ e dispara e-mails (ex.: boas-vindas no cadastro de usuário) | https://github.com/SAMUELMARQUES692/message-service |

Fluxo resumido:

```
                      (1) login              (2) JWT assinado (RSA)
  Cliente  ─────────────────────────►  auth-service  ─────────────► Cliente
                                             │
                                             │ (evento de usuário via RabbitMQ)
                                             ▼
                                      message-service ──► envio de e-mail

  Cliente  ── requisição + Bearer JWT ──►  ProlabSystem
                                             │
                                             └─ valida assinatura com a chave pública RSA
```

- **Autenticação:** JWT RSA. O `auth-service` assina com a chave privada; o `ProlabSystem` só conhece a chave pública, então valida sem precisar falar com o auth em cada request (stateless).
- **Mensageria:** comunicação assíncrona entre `auth-service` e `message-service` via RabbitMQ (event-driven), desacoplando o envio de e-mail do fluxo de cadastro.

## Stack

- Java 17
- Spring Boot 4.1.0 (Web MVC, Data JPA, Security, OAuth2 Resource Server, Validation)
- PostgreSQL
- Flyway (versionamento do schema — migrations V1 a V14)
- MapStruct (mapeamento entity ↔ DTO)
- Lombok
- springdoc-openapi (Swagger UI)
- Testes: JUnit 5, Spring Boot Test, Testcontainers (PostgreSQL)

## Organização do código

```
prolab.system
├── configuration   # SecurityConfig, RsaKeyConfig, SwaggerConfig
├── controller      # endpoints REST
├── documentation   # interfaces com as anotações OpenAPI (mantêm os controllers limpos)
├── dto (request/response)
├── entity          # entidades JPA
├── enums           # status e tipos de domínio
├── exception       # exceptions de negócio
├── handler         # GlobalExceptionHandler (@RestControllerAdvice)
├── mapper          # MapStruct
├── repository      # Spring Data JPA
└── service         # regras de negócio
```

O schema é totalmente controlado por Flyway (`ddl-auto=none`) — nada de geração automática de tabela pelo Hibernate.

## Como rodar

### Pré-requisitos
- JDK 17+
- PostgreSQL rodando
- Um par de chaves RSA (a pública é usada aqui; a privada fica no `auth-service`)
- O `auth-service` no ar para emitir tokens

### Variáveis de ambiente

| Variável | Descrição |
|---|---|
| `DATABASE_URL` | JDBC do Postgres, ex.: `jdbc:postgresql://localhost:5432/prolab` |
| `DATABASE_USERNAME` | usuário do banco |
| `DATABASE_PASSWORD` | senha do banco |
| `PATH_PUBLIC_KEY` | caminho para o arquivo `.pem` da chave **pública** RSA (ex.: `classpath:keys/public.pem` ou `file:/caminho/public.pem`) |

### Executando

```bash
# na raiz do projeto
./mvnw spring-boot:run
```

O Flyway aplica as migrations automaticamente na subida. A API sobe em `http://localhost:8080`.

### Documentação interativa (Swagger)

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v1/api-docs`

Esses dois endpoints são públicos; todo o resto exige autenticação.

## Autenticação e autorização

Toda requisição (fora o Swagger) precisa de um header:

```
Authorization: Bearer <token>
```

O token é obtido no `auth-service`:

```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "seu@email.com", "senha": "suaSenha"}'
```

Regras de acesso (por scope do JWT):

- **GET** → `SCOPE_ADMIN` ou `SCOPE_USER`
- **POST / PUT / PATCH / DELETE** → `SCOPE_ADMIN`

## Endpoints

Base: `/api`

### Clientes — `/api/clientes`
- `POST /` — cadastra cliente
- `PUT /{id}` — atualiza
- `DELETE /{id}` — remove
- `GET /{id}` — busca por id
- `GET /` — lista todos
- `GET /buscar-razao?razaoSocial=` — busca por razão social

### Caminhões — `/api/caminhoes`
- `POST /` · `PUT /{id}` · `DELETE /{id}`
- `GET /` — lista todos

### Documentos do cliente — `/api/documentos`
- `POST /` · `PUT /{id}` · `DELETE /{id}` · `GET /{id}`

### Agendamentos — `/api/agendamentos`
- `POST /` · `PUT /{id}` · `DELETE /{id}`
- `GET /cliente/{clienteId}` — agendamentos de um cliente
- `GET /status/{status}` — filtra por status
- `GET /buscar-tipo?tipo=` — filtra por tipo de destruição

### Recebimentos — `/api/recebimentos`
- `POST /` · `PUT /{id}` · `DELETE /{id}`

### Posições de estoque — `/api/posicoes`
- `POST /` · `PUT /{id}` · `DELETE /{id}`
- `GET /` — lista todas
- `GET /{codigo}/residuos` — resíduos armazenados em uma posição
- `GET /posicao-status?status=` — filtra por status da posição

### Resíduos — `/api/residuos`
- `POST /` · `PUT /{id}` · `DELETE /{id}` · `GET /{id}`
- `PATCH /{id}/status` — avança o status do resíduo (fluxo controlado: `ARMAZENADO → EM_TRATAMENTO → DESTRUIDO`)
- `GET /{tipoResiduo}/tipo` — filtra por tipo
- `GET /{posicaoId}/posicao` — filtra por posição
- `GET /status-residuo?status=` — filtra por status

## Regras de negócio (destaques)

- **Controle de capacidade:** ao cadastrar um resíduo, o serviço soma a quantidade já armazenada na posição e bloqueia a operação se exceder a capacidade (`CapacidadeExcedidaException`).
- **Máquina de estados do resíduo:** transições de status são validadas; só é permitido `ARMAZENADO → EM_TRATAMENTO → DESTRUIDO`. Ao chegar em `DESTRUIDO`, registra a data de destinação.
- **Tratamento de erros centralizado:** um `@RestControllerAdvice` traduz as exceptions de negócio em respostas HTTP consistentes.

## Testes

```bash
./mvnw test
```

A suíte cobre três camadas com ferramentas específicas para cada uma:

- **Mapper** — testes unitários puros (JUnit 5), validando a conversão MapStruct entre entidades e DTOs (Request → Entity, Entity → Response, e atualização parcial via `@MappingTarget`).
- **Service** — testes unitários com **Mockito**, isolando a lógica de negócio dos repositórios e mappers (mockados via `@Mock`/`@InjectMocks`). Cobrem os fluxos de CRUD, buscas customizadas e regras de negócio (ex.: geração do código Prime, reaproveitamento de caminhão por placa, transição de status do resíduo).
- **Controller** — testes de integração com **Testcontainers**, subindo um PostgreSQL real em container e exercitando a API de ponta a ponta via `MockMvc`, incluindo autenticação simulada com `jwt().authorities(...)` para validar as regras de autorização por scope.

Todas as sete entidades do domínio (Cliente, Caminhão, Agendamento, Recebimento, DocumentoCliente, PosicaoEstoque, Resíduo) têm cobertura nas três camadas.

Projeto de portfólio — Samuel Marques.
