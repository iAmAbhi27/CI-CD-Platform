package com.cicd.platform.pipeline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipelineRunTest {

    private PipelineRun newRun() {
        return new PipelineRun("https://github.com/me/app", "main", "abc123");
    }

    @Test
    void newRunIsQueued() {
        assertEquals(RunStatus.QUEUED, newRun().getStatus());
    }

    @Test
    void markRunningSetsStatusAndStartTime() {
        PipelineRun run = newRun();
        run.markRunning();
        assertEquals(RunStatus.RUNNING, run.getStatus());
        assertNotNull(run.getStartedAt());
    }

    @Test
    void markFinishedSuccessAndFailure() {
        PipelineRun ok = newRun();
        ok.markRunning();
        ok.markFinished(true);
        assertEquals(RunStatus.SUCCESS, ok.getStatus());
        assertNotNull(ok.getFinishedAt());

        PipelineRun bad = newRun();
        bad.markRunning();
        bad.markFinished(false);
        assertEquals(RunStatus.FAILED, bad.getStatus());
    }

    @Test
    void cannotFinishARunThatNeverStarted() {
        assertThrows(IllegalStateException.class, () -> newRun().markFinished(true));
    }

    @Test
    void cannotStartTwice() {
        PipelineRun run = newRun();
        run.markRunning();
        assertThrows(IllegalStateException.class, run::markRunning);
    }
}
