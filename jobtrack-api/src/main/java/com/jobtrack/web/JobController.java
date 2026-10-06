package com.jobtrack.web;

import com.jobtrack.model.Job;
import com.jobtrack.model.Stage;
import com.jobtrack.service.JobService;
import com.jobtrack.web.Dtos.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService service;

    public JobController(JobService service) { this.service = service; }

    @GetMapping
    public List<Job> list(@RequestParam(required = false) Stage stage) { return service.list(stage); }

    @GetMapping("/stats")
    public StatsResponse stats() { return service.stats(); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Job create(@Valid @RequestBody JobRequest r) { return service.create(r); }

    @PutMapping("/{id}")
    public Job update(@PathVariable Long id, @Valid @RequestBody JobRequest r) { return service.update(id, r); }

    @PatchMapping("/{id}/stage")
    public Job move(@PathVariable Long id, @Valid @RequestBody StageRequest r) { return service.move(id, r.stage()); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
