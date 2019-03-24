package com.utku.ppmtool.repository;

import com.utku.ppmtool.domain.Backlog;
import com.utku.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {
    Backlog findByProjectIdentifier(String projectIdentifier);
}
