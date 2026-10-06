package com.jobtrack.service;

import com.jobtrack.model.Job;
import com.jobtrack.model.Stage;
import com.jobtrack.repo.JobRepository;
import com.jobtrack.web.Dtos.JobRequest;
import com.jobtrack.web.Dtos.StatsResponse;
import com.jobtrack.web.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobService {
    private final JobRepository repo;

    public JobService(JobRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public List<Job> list(Stage stage) { return stage == null ? repo.findAll() : repo.findByStage(stage); }

    public Job create(JobRequest r) { Job j = new Job(); j.apply(r); return repo.save(j); }

    public Job update(Long id, JobRequest r) { Job j = find(id); j.apply(r); return j; }

    public Job move(Long id, Stage stage) { Job j = find(id); j.setStage(stage); return j; }

    public void delete(Long id) { repo.delete(find(id)); }

    @Transactional(readOnly = true)
    public StatsResponse stats() {
        long total = repo.count();
        long applied = total - repo.countByStage(Stage.WISHLIST);
        long interviews = repo.countByStage(Stage.INTERVIEW);
        long offers = repo.countByStage(Stage.OFFER);
        double rate = applied == 0 ? 0 : Math.round((interviews + offers) * 1000.0 / applied) / 10.0;
        long due = repo.countByFollowUpLessThanEqualAndStageNotIn(LocalDate.now(), List.of(Stage.OFFER, Stage.REJECTED));
        return new StatsResponse(total, applied, interviews, offers, rate, due);
    }

    private Job find(Long id) { return repo.findById(id).orElseThrow(() -> new NotFoundException(id)); }
}
