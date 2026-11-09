package com.cicd.platform.pipeline;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import java.time.Instant;

@Entity
@Table(name = "pipeline_run")
public class PipelineRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repo_url", nullable = false)
    private String repoUrl;

    @Column(nullable = false)
    private String branch;

    @Column(name = "commit_sha")
    private String commitSha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RunStatus status = RunStatus.QUEUED;

    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    protected PipelineRun() {}   

    public PipelineRun(String repoUrl, String branch, String commitSha) {
        this.repoUrl = repoUrl;
        this.branch = branch;
        this.commitSha = commitSha;
    }

    public Long getId() { return id; }
    public String getRepoUrl() { return repoUrl; }
    public String getBranch() { return branch; }
    public String getCommitSha() { return commitSha; }
    public RunStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getFinishedAt() { return finishedAt; }

    public void markRunning() {
        if (status != RunStatus.QUEUED) {
            throw new IllegalStateException("Cannot start a run that is " + status);
        }
        this.status = RunStatus.RUNNING;
        this.startedAt = Instant.now();
    }

    public void markFinished(boolean success) {
        if (status != RunStatus.RUNNING) {
            throw new IllegalStateException("Cannot finish a run that is " + status);
        }
        this.status = success ? RunStatus.SUCCESS : RunStatus.FAILED;
        this.finishedAt = Instant.now();
    }
}
