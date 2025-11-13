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
    - `modelo/` — entidades JPA (Usuario, Competencia, Curso, TrilhaAprendizagem, etc.)
    - `repositorio/` — repositórios Spring Data
    - `service/` — regras de negócio (a criar)
    - `controller/` — endpoints REST/Thymeleaf (a criar)
    - `config/` — segurança, cache, i18n, mensageria (a criar)
  - `src/main/resources/`
    - `application.yml` — configurações (dev/prod)
    - `i18n/` — arquivos de mensagens (a criar)
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
# na raiz do projeto
mvn spring-boot:run -pl skillup -Dspring-boot.run.profiles=dev
```

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
- Perfis: `ROLE_ADMIN`, `ROLE_USUARIO`
- Fluxo: login → emissão de token → autorização por rotas
- Coloque o token no header `Authorization: Bearer <token>`

---

## Internacionalização (i18n)
- Arquivos em `src/main/resources/i18n/mensagens_*.properties`
- Idiomas: `pt-BR` e `en-US` (a criar)

---

## Cache
- Cursos recomendados e listagens podem usar cache (`@Cacheable`)
- Dev: Caffeine. Prod: Redis (opcional, via profile `prod`)

---

## Paginação
- Utilize `Pageable` nos endpoints: `GET /cursos?page=0&size=20&sort=titulo,asc`

---

## Tratamento de erros
- `@ControllerAdvice` centralizando respostas amigáveis (a criar)

---

## Mensageria (RabbitMQ)
- Fila para processamento assíncrono de recomendações de IA (producer/consumer a criar)

---

## IA Generativa
- Endpoint alvo: `GET /api/recomendacoes/{usuarioId}`
- Integração com API de IA (ex.: OpenAI) usando chave e modelo configuráveis

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

## Roadmap (próximos passos)
- Controladores REST (Usuario, Competencia, Curso, Trilha)
- Serviços com cache e paginação
- Autenticação JWT e autorização por perfil
- Producer/Consumer RabbitMQ
- Integração real com IA no endpoint de recomendações
- Migrations Flyway e dados seed
- UI simples com Thymeleaf (ou frontend React)

---

## Licença
Projeto acadêmico — Global Solution FIAP 2025/2 (Java Advanced).
