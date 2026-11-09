package com.cicd.platform.pipeline;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/runs")
public class PipelineRunController {

    private final PipelineRunRepository repository;

    public PipelineRunController(PipelineRunRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RunResponse create(@RequestBody CreateRunRequest request) {
        PipelineRun run = new PipelineRun(request.repoUrl(), request.branch(), request.commitSha());
        PipelineRun saved = repository.saveAndFlush(run);
        return RunResponse.from(saved);
    }

    @GetMapping
    public List<RunResponse> list() {
        return repository.findAll().stream().map(RunResponse::from).toList();
    }

    @GetMapping("/{id}")
    public RunResponse get(@PathVariable Long id) {
        return repository.findById(id)
                .map(RunResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found"));
    }
}
