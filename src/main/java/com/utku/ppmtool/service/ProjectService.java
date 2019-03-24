package com.utku.ppmtool.service;

import com.utku.ppmtool.domain.Backlog;
import com.utku.ppmtool.domain.Project;
import com.utku.ppmtool.domain.User;
import com.utku.ppmtool.exception.ProjectIdException;
import com.utku.ppmtool.exception.ProjectNotFoundException;
import com.utku.ppmtool.repository.BacklogRepository;
import com.utku.ppmtool.repository.ProjectRepository;
import com.utku.ppmtool.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    public ProjectService(ProjectRepository projectRepository, BacklogRepository backlogRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.userRepository = userRepository;
    }

    private final ProjectRepository projectRepository;

    private final BacklogRepository backlogRepository;

    private final UserRepository userRepository;

    public Project save(Project project, String username) {

        if (project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if (existingProject != null && !existingProject.getProjectLeader().equals(username))
                throw new ProjectNotFoundException("Project not found in your account");
            else if (existingProject == null)
                throw new ProjectNotFoundException("Project with ID: " + project.getProjectIdentifier() + " doesn't exist");
        }

        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier());
            } else {
                Backlog backlog = backlogRepository.findByProjectIdentifier(project.getProjectIdentifier());
                project.setBacklog(backlog);
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID: " + project.getProjectIdentifier() + " already exists");
        }
    }

    public Project findByProjectIdentifier(String projectId, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null)
            throw new ProjectIdException("Project ID: " + projectId + " doesn't exist");
        else if (!project.getProjectLeader().equals(username))
            throw new ProjectNotFoundException("Project not found in your account");
        else
            return project;
    }

    public Iterable<Project> findAll(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteByProjectIdentifier(String projectId, String username) {
        projectRepository.delete(findByProjectIdentifier(projectId, username));
    }
}
