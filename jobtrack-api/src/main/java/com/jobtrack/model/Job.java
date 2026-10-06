package com.jobtrack.model;

import com.jobtrack.web.Dtos.JobRequest;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    private String role;
    private String location;
    private String source;
    @Enumerated(EnumType.STRING)
    private Stage stage;
    private LocalDate followUp;
    private String url;
    @Column(length = 2000)
    private String notes;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags = new ArrayList<>();
    private Instant createdAt = Instant.now();

    public void apply(JobRequest r) {
        company = r.company(); role = r.role(); location = r.location(); source = r.source();
        stage = r.stage(); followUp = r.followUp(); url = r.url(); notes = r.notes();
        tags = r.tags() == null ? new ArrayList<>() : new ArrayList<>(r.tags());
    }

    public Long getId() { return id; }
    public String getCompany() { return company; }
    public String getRole() { return role; }
    public String getLocation() { return location; }
    public String getSource() { return source; }
    public Stage getStage() { return stage; }
    public void setStage(Stage stage) { this.stage = stage; }
    public LocalDate getFollowUp() { return followUp; }
    public String getUrl() { return url; }
    public String getNotes() { return notes; }
    public List<String> getTags() { return tags; }
    public Instant getCreatedAt() { return createdAt; }
}
