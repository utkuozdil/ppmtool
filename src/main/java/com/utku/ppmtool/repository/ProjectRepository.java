package com.utku.ppmtool.repository;

import com.utku.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projectId);
    Iterable<Project> findAllByProjectLeader(String username);
}
