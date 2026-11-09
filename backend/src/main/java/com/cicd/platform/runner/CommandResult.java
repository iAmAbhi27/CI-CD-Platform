package com.cicd.platform.runner;

public record CommandResult(int exitCode, boolean timedOut, String output) {
}
