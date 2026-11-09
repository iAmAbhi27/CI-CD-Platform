package com.cicd.platform.runner;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandExecutorTest {

    private final CommandExecutor executor = new CommandExecutor();

    @Test
    void capturesOutputAndExitCodeZero() throws Exception {
        CommandResult result = executor.run(List.of("echo", "hello"), Duration.ofSeconds(5), line -> {});
        assertEquals(0, result.exitCode());
        assertTrue(result.output().contains("hello"));
        assertFalse(result.timedOut());
    }

    @Test
    void reportsNonZeroExitCode() throws Exception {
        CommandResult result = executor.run(
                List.of("sh", "-c", "echo oops; exit 3"), Duration.ofSeconds(5), line -> {});
        assertEquals(3, result.exitCode());
        assertTrue(result.output().contains("oops"));
    }

    @Test
    void killsProcessOnTimeout() throws Exception {
        CommandResult result = executor.run(List.of("sleep", "10"), Duration.ofSeconds(1), line -> {});
        assertTrue(result.timedOut());
        assertEquals(-1, result.exitCode());
    }

    @Test
    void runsADockerContainer() throws Exception {
        CommandResult result = executor.run(
            List.of("docker", "run", "--rm", "hello-world"), Duration.ofSeconds(60), line -> {});
        assertEquals(0, result.exitCode());
        assertTrue(result.output().contains("Hello from Docker"));
    }
}
