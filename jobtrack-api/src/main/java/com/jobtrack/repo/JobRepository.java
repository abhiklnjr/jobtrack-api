package com.jobtrack.repo;

import com.jobtrack.model.Job;
import com.jobtrack.model.Stage;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByStage(Stage stage);
    long countByStage(Stage stage);
    long countByFollowUpLessThanEqualAndStageNotIn(LocalDate date, Collection<Stage> stages);
}
