# Requirements

## 1. Tools to install (developer machine)

| Tool | Version | Needed from | Purpose |
|---|---|---|---|
| Java JDK | 21 (LTS) | Phase 0 | Compile and run the backend |
| Maven | 3.9+ | Phase 0 | Build tool / dependencies |
| Docker + Compose | 24+ / v2 | Phase 0 | Postgres, runners, packaging |
| Git | 2.30+ | Phase 0 | Source control |
| Node.js | 20 LTS | Phase 3 | Frontend (React) |
| kubectl + Helm | latest | Phase 4 | Deployments |
| Terraform | 1.6+ | Phase 5 | Infra as code (optional) |

## 2. Backend libraries (added step by step)

| Library | Phase | Purpose |
|---|---|---|
| spring-boot-starter-web | 0 | REST API |
| spring-boot-starter-actuator | 0 | Health / metrics |
| spring-boot-starter-data-jpa + postgresql | 0 | Database access |
| flyway-core | 0 | DB migrations |
| spring-boot-starter-validation | 1 | Request validation |
| spring-boot-starter-security + jjwt | 3 | Auth / JWT |
| spring-boot-starter-amqp (or spring-kafka) | 2 | Job queue |
| docker-java | 1 | Talk to Docker from Java |
| snakeyaml | 2 | Parse pipeline.yml |
| micrometer-registry-prometheus | 5 | Metrics |
| Spring AI / Anthropic SDK | 6 | AI features |

## 3. Functional requirements

1. Receive Git webhooks (push, pull request) and verify signatures.
2. Define pipelines as code (`pipeline.yml`) with stages, steps, parallel jobs.
3. Execute each step in an isolated container and capture logs.
4. Track run/job state: QUEUED, RUNNING, SUCCESS, FAILED, CANCELLED.
5. Store artifacts and logs; stream logs live to the UI.
6. Manage projects, users, roles, and secrets.
7. Build images, push to a registry, deploy to Kubernetes, roll back.
8. Notify on completion (Slack / email).
9. AI: explain failures, review diffs, score deployment risk (optional, async).

## 4. Non-functional requirements

- **Security:** untrusted code runs unprivileged, with CPU/memory limits; secrets masked in logs.
- **Reliability:** idempotent webhooks; runs recover after a restart.
- **Scalability:** runners scale independently of the API.
- **Observability:** metrics, structured logs, tracing.
- **Maintainability:** modular monolith, one package per module, migrations for every schema change.
- **Degradation:** AI features must never block or fail a pipeline.

## 5. Roadmap

| Phase | Goal |
|---|---|
| 0 | Foundations: app skeleton, DB |
| 1 | MVP engine: webhook, container run, logs |
| 2 | Pipelines as code, DAG, queue |
| 3 | UI, auth, artifacts |
| 4 | CD: registry, Kubernetes, rollback |
| 5 | Hardening: secrets, RBAC, metrics |
| 6 | AI layer |
