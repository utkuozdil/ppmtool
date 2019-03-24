package com.utku.ppmtool.service;

import com.utku.ppmtool.domain.Backlog;
import com.utku.ppmtool.domain.ProjectTask;
import com.utku.ppmtool.exception.ProjectNotFoundException;
import com.utku.ppmtool.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, ProjectService projectService) {
        this.projectTaskRepository = projectTaskRepository;
        this.projectService = projectService;
    }

    private final ProjectTaskRepository projectTaskRepository;

    private final ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier, username).getBacklog();

        projectTask.setBacklog(backlog);
        projectTask.setProjectIdentifier(projectIdentifier);

        Integer backlogSequence = backlog.getPTSequence() + 1;
        backlog.setPTSequence(backlogSequence);
        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);

        if (projectTask.getPriority() == null || projectTask.getPriority() == 0)
            projectTask.setPriority(3);

        if (projectTask.getStatus() == null || projectTask.getStatus().equals(""))
            projectTask.setStatus("TO_DO");

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {
        projectService.findByProjectIdentifier(id, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String id, String projectSequence, String username) {
        projectService.findByProjectIdentifier(id, username);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence);
        if (projectTask == null)
            throw new ProjectNotFoundException("ProjectTask with ID: " + projectSequence + " doesn't exist");
        else if (!projectTask.getProjectIdentifier().equals(id))
            throw new ProjectNotFoundException("ProjectTask with ID: " + projectSequence + " doesn't exist in project with ID: " + id);
        else
            return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String id, String projectSequence, String username) {
        findProjectTaskByProjectSequence(id, projectSequence, username);
        return projectTaskRepository.save(updatedProjectTask);
    }

    public void deleteByProjectSequence(String id, String projectSequence, String username) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(id, projectSequence, username);
        projectTaskRepository.delete(projectTask);
    }
}
