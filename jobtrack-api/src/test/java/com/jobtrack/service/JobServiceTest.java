package com.jobtrack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jobtrack.model.Stage;
import com.jobtrack.repo.JobRepository;
import com.jobtrack.web.Dtos.StatsResponse;
import org.junit.jupiter.api.Test;

class JobServiceTest {
    @Test
    void statsComputesResponseRateFromAppliedJobs() {
        JobRepository repo = mock(JobRepository.class);
        when(repo.count()).thenReturn(10L);
        when(repo.countByStage(Stage.WISHLIST)).thenReturn(2L);
        when(repo.countByStage(Stage.INTERVIEW)).thenReturn(2L);
        when(repo.countByStage(Stage.OFFER)).thenReturn(1L);

        StatsResponse s = new JobService(repo).stats();

        assertEquals(8, s.applied());
        assertEquals(37.5, s.responseRatePercent());
    }

    @Test
    void statsHandlesEmptyBoard() {
        StatsResponse s = new JobService(mock(JobRepository.class)).stats();
        assertEquals(0, s.responseRatePercent());
    }
}
