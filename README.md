# CI/CD Platform

A CI/CD platform built from scratch with Java 21 and Spring Boot, with AI features planned.

Status: structure only, nothing implemented yet. See `REQUIREMENTS.md` and `.env.example`.

## Layout

| Path | Purpose |
|---|---|
| `backend/` | Spring Boot modular monolith (one package per module) |
| `backend/.../webhook` | Receives Git events |
| `backend/.../pipeline` | Pipeline definitions and runs |
| `backend/.../orchestrator` | Schedules jobs, tracks state |
| `backend/.../runner` | Executes steps in containers |
| `backend/.../artifact`, `logs` | Build outputs and log streaming |
| `backend/.../deploy` | Registry push, Kubernetes deploy, rollback |
| `backend/.../auth`, `secrets`, `project` | Users, secrets, projects |
| `backend/.../notification` | Slack / email |
| `backend/.../ai` | AI insights (optional, async) |
| `frontend/` | React dashboard |
| `runner-images/` | Docker images used for build steps |
| `infra/` | Docker, Kubernetes, Helm, Terraform |
| `examples/pipelines/` | Sample `pipeline.yml` files |
| `docs/` | Architecture, ADRs, API docs |
| `scripts/` | Helper scripts |
| `.github/workflows/` | CI for the platform itself |
