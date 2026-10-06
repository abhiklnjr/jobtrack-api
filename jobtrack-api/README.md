# JobTrack API

Spring Boot REST backend for a job-search tracker (Kanban pipeline: Wishlist → Applied → Interview → Offer → Rejected).

**Stack:** Java 17, Spring Boot 3, Spring Data JPA, Bean Validation, PostgreSQL (H2 for local dev), JUnit 5 + Mockito, Docker, Google Cloud Run.

## Run locally
    mvn spring-boot:run        # H2 in-memory, http://localhost:8080
    mvn test

## Endpoints
| Method | Path | Purpose |
|---|---|---|
| GET | /api/jobs?stage=APPLIED | List jobs, optional stage filter |
| POST | /api/jobs | Create job (validated) |
| PUT | /api/jobs/{id} | Update job |
| PATCH | /api/jobs/{id}/stage | Move between stages (`{"stage":"INTERVIEW"}`) |
| DELETE | /api/jobs/{id} | Delete job |
| GET | /api/jobs/stats | Totals, response rate, follow-ups due |

Example: `curl -X POST localhost:8080/api/jobs -H 'Content-Type: application/json' -d '{"company":"Acme","role":"Java Developer","stage":"APPLIED","tags":["java"]}'`

## Deploy to Cloud Run
    gcloud run deploy jobtrack-api --source . --region northamerica-northeast2 \
      --set-env-vars DB_URL=jdbc:postgresql:///jobtrack?cloudSqlInstance=PROJECT:REGION:INSTANCE\&socketFactory=com.google.cloud.sql.postgres.SocketFactory,DB_USER=...,ALLOWED_ORIGIN=https://your-frontend
(Add the `postgres-socket-factory` dependency and store DB_PASSWORD in Secret Manager.)

## Roadmap
- [ ] JWT auth (Spring Security) so each user sees only their jobs
- [ ] Flyway migrations instead of ddl-auto
- [ ] Integration tests with Testcontainers
- [ ] Cloud Scheduler + endpoint for follow-up reminder emails
- [ ] GitHub Actions CI (build, test, deploy)
- [ ] Connect the front-end board to this API
