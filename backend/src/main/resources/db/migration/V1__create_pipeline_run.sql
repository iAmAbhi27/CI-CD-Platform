CREATE TABLE pipeline_run (
    id          BIGSERIAL PRIMARY KEY,
    repo_url    VARCHAR(500) NOT NULL,
    branch      VARCHAR(255) NOT NULL,
    commit_sha  VARCHAR(64),
    status      VARCHAR(20)  NOT NULL,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    started_at  TIMESTAMPTZ,
    finished_at TIMESTAMPTZ
);