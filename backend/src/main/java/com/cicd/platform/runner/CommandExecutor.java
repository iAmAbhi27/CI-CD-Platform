package com.cicd.platform.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class CommandExecutor {

    public CommandResult run(List<String> command, Duration timeout, Consumer<String> onLine)
            throws IOException, InterruptedException {

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();   

        StringBuilder output = new StringBuilder();

        Thread reader = Thread.ofVirtual().start(() -> {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = in.readLine()) != null) {
                    output.append(line).append('\n');
                    onLine.accept(line);
                }
            } catch (IOException ignored) {
                // stream closed because the process ended or was killed
            }
        });

        boolean finished = process.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);
        if (!finished) {
            process.destroyForcibly();
            process.waitFor();
        }
        reader.join();

        return new CommandResult(finished ? process.exitValue() : -1, !finished, output.toString());
    }
}
