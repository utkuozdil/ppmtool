package com.utku.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class ProjectTask extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String projectSequence;

    @NonNull
    @NotBlank(message = "summary can't be empty")
    private String summary;

    private String acceptanceCriteria;

    private String status;

    private Integer priority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @Column(updatable = false)
    private String projectIdentifier;

    @JsonIgnore
    @NonNull
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Backlog backlog;


}
