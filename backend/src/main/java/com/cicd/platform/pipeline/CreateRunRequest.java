package com.cicd.platform.pipeline;

public record CreateRunRequest(String repoUrl, String branch, String commitSha) {
}
