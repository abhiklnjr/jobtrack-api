package com.jobtrack.web;

import com.jobtrack.model.Stage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public final class Dtos {
    private Dtos() {}

    public record JobRequest(@NotBlank String company, @NotBlank String role, String location, String source,
                             @NotNull Stage stage, LocalDate followUp, String url, List<String> tags, String notes) {}

    public record StageRequest(@NotNull Stage stage) {}

    public record StatsResponse(long total, long applied, long interviews, long offers,
                                double responseRatePercent, long followUpsDue) {}
}
