package com.utku.ppmtool.bootstrap;

import com.utku.ppmtool.domain.Backlog;
import com.utku.ppmtool.domain.Project;
import com.utku.ppmtool.domain.User;
import com.utku.ppmtool.service.ProjectService;
import com.utku.ppmtool.service.ProjectTaskService;
import com.utku.ppmtool.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    public DatabaseLoader(ProjectService projectService, ProjectTaskService projectTaskService, UserService userService) {
        this.projectService = projectService;
        this.projectTaskService = projectTaskService;
        this.userService = userService;
    }

    private final ProjectService projectService;

    private final ProjectTaskService projectTaskService;

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        addProjects();
    }

    private void addProjects() {
        User user = new User();
        user.setUsername("admin@gmail.com");
        user.setPassword("password");
        user.setFullName("Admin admin");
        userService.saveUser(user);

        Project project1 = new Project("Test", "ID01", "new project");
        Backlog backlog1 = new Backlog();
        project1.setBacklog(backlog1);
        backlog1.setProject(project1);
        backlog1.setProjectIdentifier(project1.getProjectIdentifier());

        projectService.save(project1, "admin@gmail.com");
//        ProjectTask projectTask1 = new ProjectTask("test 1", backlog1);
//        projectTaskService.addProjectTask(backlog1.getProjectIdentifier(), projectTask1);

        Project project2 = new Project("Test 2", "ID02", "second project");
        Backlog backlog2 = new Backlog();
        project2.setBacklog(backlog2);
        backlog2.setProject(project2);
        backlog2.setProjectIdentifier(project2.getProjectIdentifier());

        projectService.save(project2, "admin@gmail.com");
//        ProjectTask projectTask2 = new ProjectTask("test 2", backlog2);
//        projectTaskService.addProjectTask(backlog2.getProjectIdentifier(), projectTask2);

        Project project3 = new Project("Test 3", "ID03", "third project");
        Backlog backlog3 = new Backlog();
        project3.setBacklog(backlog3);
        backlog3.setProject(project3);
        backlog3.setProjectIdentifier(project3.getProjectIdentifier());

        projectService.save(project3, "admin@gmail.com");
//        ProjectTask projectTask3 = new ProjectTask("test 3", backlog3);
//        projectTaskService.addProjectTask(backlog3.getProjectIdentifier(), projectTask3);
    }
}
