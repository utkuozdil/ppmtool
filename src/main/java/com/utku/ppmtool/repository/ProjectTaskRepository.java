package com.utku.ppmtool.repository;

import com.utku.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);
    ProjectTask findByProjectSequence(String projectSequence);
}
