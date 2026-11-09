package com.cicd.platform.pipeline;

import java.time.Instant;

public record RunResponse(Long id, String repoUrl, String branch, String commitSha,
                          RunStatus status, Instant createdAt) {

    public static RunResponse from(PipelineRun run) {
        return new RunResponse(run.getId(), run.getRepoUrl(), run.getBranch(),
                run.getCommitSha(), run.getStatus(), run.getCreatedAt());
    }
}
