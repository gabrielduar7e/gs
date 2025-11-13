# gs
# SkillUp — Plataforma de Requalificação Profissional (Java Spring Boot)

SkillUp é uma plataforma web que ajuda profissionais a se requalificarem para o futuro do trabalho. Usando IA generativa e análise de dados, o sistema recomenda cursos e trilhas de aprendizagem personalizadas com base no perfil, nas competências e nas tendências do mercado.

Alinhado aos ODS 4 (Educação de Qualidade) e ODS 8 (Trabalho Decente e Crescimento Econômico).

---

## Stack e principais requisitos
- Spring Boot 3 (Web/MVC + REST)
- Spring Data JPA (Oracle)
- Bean Validation
- Spring Cache (Caffeine/Redis)
- Internacionalização (pt-BR, en-US)
- Paginação (Spring Data Pageable)
- Spring Security (JWT) com perfis ADMIN e USUARIO
- Tratamento de erros com ControllerAdvice
- Mensageria (RabbitMQ)
- Integração com IA (endpoint de recomendações)
- Migrations (Flyway)
- Deploy no Azure App Service + CI/CD

---

## Estrutura do projeto (resumo)
- `skillup/`
  - `src/main/java/com/fiap/skillup/` — código fonte
    - `SkillUpApplication.java` — aplicação principal
    - `model/` — entidades JPA (Usuario, Competencia, Curso, TrilhaAprendizagem, etc.)
    - `repository/` — repositórios Spring Data
    - `service/` — regras de negócio (cache, paginação, recomendações)
    - `controller/` — endpoints REST
    - `security/` — JWT, filtros e configuração
    - `messaging/` — RabbitMQ (exchange/queue/binding, producer/consumer)
    - `config/` — i18n, OpenAPI, seeds dev
  - `src/main/resources/`
    - `application.yml` — configurações (dev/prod)
    - `i18n/` — arquivos de mensagens (pt-BR/en)
    - `static/` — frontend estático (index.html, app.js, styles.css)
  - `pom.xml`

---

## Pré-requisitos
- JDK 17+
- Maven 3.9+
- Oracle Database (local ou em nuvem)
- RabbitMQ (local ou em nuvem)
- Conta no Azure (para deploy) e GitHub (para CI/CD)

---

## Configuração
Edite `src/main/resources/application.yml`:
- Datasource Oracle: `spring.datasource.url`, `username`, `password`
- JPA: `hibernate.dialect = Oracle12c`
- Cache: Caffeine (dev) e Redis (prod, opcional)
- RabbitMQ: host, porta e credenciais
- Segurança (JWT): segredo e expiração
- IA: `aplicacao.ia.chave-api` e `endpoint`

Perfis ativos:
- `dev`: `spring.profiles.active=dev` (DDL auto update)
- `prod`: `spring.profiles.active=prod` (DDL validate)

---

## Como executar (dev)
```powershell
# na pasta skillup
$env:SPRING_PROFILES_ACTIVE="dev"; mvn spring-boot:run
```

- Base URL: `http://localhost:8081/api`
- H2 Console: `http://localhost:8081/api/h2-console` (JDBC: `jdbc:h2:mem:skillup`, user: `sa`, senha: vazia)
- Swagger UI: `http://localhost:8081/api/swagger-ui/index.html`
- Frontend estático: `http://localhost:8081/api/index.html`

Build do pacote:
```powershell
mvn -q -DskipTests package -pl skillup
```

---

## Banco e migrations (Flyway)
- Scripts em `classpath:db/migration` (a criar)
- Convenção: `V1__create_tables.sql`, `V2__insert_seed.sql`, ...

---

## Segurança (JWT)
- Perfis: `ROLE_ADMIN`, `ROLE_USER`
- Fluxo: login → emissão de token → autorização por rotas
- Header: `Authorization: Bearer <token>`
- Seed (profile `dev`): `admin@skillup.com` / `Senha@123`
- Login: `POST /api/auth/login` → `{ token: "..." }`
- Autorização de rotas:
  - `GET /api/usuarios/**` e `GET /api/competencias/**`: público
  - `POST|PUT|DELETE /api/usuarios/**` e `/api/competencias/**`: `ROLE_ADMIN`

---

## Internacionalização (i18n)
- Arquivos em `src/main/resources/i18n/mensagens*.properties`
- Idiomas: `pt-BR` (default) e `en-US`
- Força idioma via query param: `?lang=en`
- Ex.: `GET /api/ping` e `GET /api/ping?lang=en`

---

## Cache
- Cursos recomendados e listagens podem usar cache (`@Cacheable`)
- Dev: Caffeine. Prod: Redis (opcional, via profile `prod`)

---

## Paginação
- Utilize `Pageable` nos endpoints: `GET /cursos?page=0&size=20&sort=titulo,asc`

---

## Tratamento de erros
- `@ControllerAdvice` centralizando respostas amigáveis (payload `ApiErro`)

---

## Mensageria (RabbitMQ)
- Exchange/Queue/Binding configurados, conversor JSON, producer/consumer habilitados
- Publicar solicitação: `POST /api/recomendacoes/{usuarioId}` (HTTP 202)
- Consumo: listener registra no log a mensagem recebida
- Requer RabbitMQ disponível (host padrão: `localhost:5672`)

---

## IA Generativa
- Endpoints:
  - `GET /api/recomendacoes/{usuarioId}/gerar` — mock (sem chave)
  - `GET /api/recomendacoes/{usuarioId}/ai` — IA com fallback para mock
- Configuração em `application.yml` (profile dev usa fallback se `aplicacao.ia.chave-api` não definida)

---

## Deploy no Azure App Service
- Empacotar com Maven e publicar imagem/artefato
- Configurar variáveis de ambiente (DB, RabbitMQ, JWT, IA)
- Opcional: usar Azure Database for Oracle, Azure Cache for Redis, Azure Service Bus (alternativa ao RabbitMQ)

---

## CI/CD (GitHub Actions)
- Workflow sugerido: build → testes → sonar (opcional) → deploy no Azure App Service
- Exemplo de gatilhos: `push` em `main` e `pull_request`

---

## Scripts úteis
```powershell
# testes
mvn -q test -pl skillup

# checar formato do código (spotless/checkstyle – a configurar)
# mvn spotless:apply -pl skillup
```

---

## Frontend (estático)
- `src/main/resources/static/index.html` — página demo
- Funcionalidades: login (JWT), listar usuários/competências, criar competência, recomendações (mock/IA), publicar na fila

---

## Roadmap (próximos passos)
- Banco Oracle + Flyway (profile `prod`) — você irá preparar o Oracle
- Consumer persistindo recomendações geradas (IA) e endpoint para consulta
- Deploy no Azure App Service + CI/CD (GitHub Actions)

---

## Licença
Projeto acadêmico — Global Solution FIAP 2025/2 (Java Advanced).
