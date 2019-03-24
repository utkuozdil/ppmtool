package com.utku.ppmtool.web;

import com.utku.ppmtool.domain.Project;
import com.utku.ppmtool.service.MapValidationErrorService;
import com.utku.ppmtool.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    public ProjectController(ProjectService projectService, MapValidationErrorService mapValidationErrorService) {
        this.projectService = projectService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    private final ProjectService projectService;

    private final MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult, Principal principal) {
        ResponseEntity<?> errorResponseEntity = mapValidationErrorService.getErrorResponseEntity(bindingResult);
        if (errorResponseEntity != null)
            return errorResponseEntity;
        else {
            project = projectService.save(project, principal.getName());
            return new ResponseEntity<>(project, HttpStatus.CREATED);
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {
        return new ResponseEntity<>(projectService.findByProjectIdentifier(projectId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getProjects(Principal principal) {
        return projectService.findAll(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId, Principal principal) {
        projectService.deleteByProjectIdentifier(projectId, principal.getName());
        return new ResponseEntity<>("Project with ID " + projectId + " was deleted", HttpStatus.OK);
    }
}
