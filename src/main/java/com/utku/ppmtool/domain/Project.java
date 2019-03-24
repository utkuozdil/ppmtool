package com.utku.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class Project extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "projectName can't be empty")
    private String projectName;

    @NonNull
    @NotBlank(message = "projectIdentifier can't be empty")
    @Size(min = 4, max = 5, message = "use 4-5 char")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @NonNull
    @NotBlank(message = "description can't be empty")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    private Backlog backlog;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String projectLeader;
}


