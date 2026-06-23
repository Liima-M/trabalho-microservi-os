# trabEcommerce — E-commerce Marketplace com Microsserviços

**UDESC CEAVI | Engenharia de Software Orientada a Serviços**  
Discentes: Matheus Teixeira · Mailon Barbetta · Elian Benck · Matheus Oliver

---

## Arquitetura

```
Cliente → API Gateway (8080)
              ├── auth-service        (8081) — JWT, cadastro, login
              ├── catalog-service     (8082) — produtos, categorias (Strategy)
              ├── inventory-service   (8083) — estoque e reservas
              ├── cart-service        (8084) — carrinho de compras
              ├── order-service       (8085) — pedidos + Saga Orchestration
              ├── payment-service     (8086) — pagamentos + Circuit Breaker
              ├── shipping-service    (8087) — frete e rastreamento
              ├── review-service      (8088) — avaliações (CQRS)
              └── notification-service(8089) — notificações (Observer/AMQP)
```

**Padrões de microsserviços aplicados:**
| Integrante | Serviços | Padrão MS | Padrão GoF |
|---|---|---|---|
| Matheus Teixeira | catalog, inventory | API Gateway | Strategy |
| Mailon Barbetta | cart, order | Saga (Orchestration) | State |
| Elian Benck | payment, shipping | Circuit Breaker | Factory Method |
| Matheus Oliver | review, notification | CQRS | Observer |

---

## Como executar

### Pré-requisitos
- Docker Desktop instalado e rodando
- Ao menos 6 GB de RAM disponível para Docker

### Subir toda a stack

```bash
cd trabEcommerce

# (Opcional) Configure variáveis de ambiente
cp .env.example .env

# Sobe todos os 10 microsserviços + bancos + RabbitMQ
docker compose up --build
```

### Parar a stack

```bash
docker compose down
# Para remover também os volumes (dados):
docker compose down -v
```

---

## Portas e endpoints principais

| Serviço | Porta | Swagger UI |
|---|---|---|
| **API Gateway** | 8080 | — |
| auth-service | 8081 | http://localhost:8081/swagger-ui.html |
| catalog-service | 8082 | http://localhost:8082/swagger-ui.html |
| inventory-service | 8083 | http://localhost:8083/swagger-ui.html |
| cart-service | 8084 | http://localhost:8084/swagger-ui.html |
| order-service | 8085 | http://localhost:8085/swagger-ui.html |
| payment-service | 8086 | http://localhost:8086/swagger-ui.html |
| shipping-service | 8087 | http://localhost:8087/swagger-ui.html |
| review-service | 8088 | http://localhost:8088/swagger-ui.html |
| notification-service | 8089 | http://localhost:8089/swagger-ui.html |
| **RabbitMQ UI** | 15672 | http://localhost:15672 (guest/guest) |

---

## Desenvolvimento no IntelliJ IDEA

1. Abra o IntelliJ e escolha **File → Open**
2. Selecione a pasta `trabEcommerce` (o `pom.xml` raiz será detectado automaticamente)
3. Aguarde o IntelliJ indexar o projeto Maven multi-módulo
4. Para rodar um serviço individualmente:
   - Expanda o módulo (ex: `auth-service`)
   - Clique com botão direito na classe `AuthServiceApplication` → **Run**
   - Certifique-se de que PostgreSQL e RabbitMQ estão rodando (use `docker compose up auth-db rabbitmq`)

### Variáveis de ambiente para rodar localmente (sem Docker)

Configure em **Run/Debug Configurations → Environment variables**:

```
DB_HOST=localhost;DB_PORT=5432;DB_USER=postgres;DB_PASS=postgres;RABBITMQ_HOST=localhost;JWT_SECRET=changeme-super-secret-key-at-least-256-bits-long!!
```

---

## Executar testes

```bash
# Todos os módulos (JaCoCo verifica cobertura mínima de 50%)
mvn verify

# Módulo específico
mvn verify -pl auth-service

# Apenas testes unitários (sem Testcontainers)
mvn test -pl catalog-service
```

---

## Estrutura de pacotes (Arquitetura Hexagonal)

Cada microsserviço segue a estrutura:

```
src/main/java/br/edu/udesc/ecommerce/<servico>/
  domain/
    model/          ← Entidades e Value Objects (puro Java, sem frameworks)
    port/
      in/           ← Interfaces dos Casos de Uso (portas de entrada)
      out/          ← Interfaces de repositórios e clientes externos (portas de saída)
    strategy/       ← Padrão Strategy (catalog-service)
    factory/        ← Padrão Factory Method (payment-service)
    saga/           ← Padrão State / Saga steps (order-service)
  application/
    usecase/        ← Implementações dos casos de uso
    projector/      ← Projector CQRS (review-service)
  infrastructure/
    persistence/    ← Adaptadores JPA e MongoDB
    messaging/      ← Adaptadores RabbitMQ (publishers e consumers)
    http/           ← Clientes REST para comunicação entre serviços
    security/       ← BCrypt, JWT (auth-service)
  presentation/
    rest/           ← Controllers REST
    dto/            ← DTOs de request/response
```

---

## Tecnologias

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 LTS | Linguagem principal |
| Spring Boot | 3.3.5 | Framework base |
| Spring Cloud Gateway | 4.x | API Gateway |
| Spring AMQP | 3.x | RabbitMQ |
| Resilience4j | 2.x | Circuit Breaker |
| Flyway | atual | Migrações de banco |
| PostgreSQL | 16 | Banco relacional (todos os serviços) |
| MongoDB | 7 | Projeção CQRS (review-service) |
| RabbitMQ | 3.13 | Message broker |
| JUnit 5 + Mockito | atual | Testes unitários |
| Testcontainers | 1.20 | Testes de integração |
| JaCoCo | 0.8.12 | Cobertura de código (mín. 50%) |
| springdoc-openapi | 2.6 | Swagger UI |
| Docker + Compose | atual | Containerização (RNF01, RNF02) |
