package com.utku.ppmtool.web;

import com.utku.ppmtool.domain.ProjectTask;
import com.utku.ppmtool.service.MapValidationErrorService;
import com.utku.ppmtool.service.ProjectTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    public BacklogController(ProjectTaskService projectTaskService, MapValidationErrorService mapValidationErrorService) {
        this.projectTaskService = projectTaskService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    private final ProjectTaskService projectTaskService;

    private final MapValidationErrorService mapValidationErrorService;


    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTasktoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
                                                     @PathVariable String backlog_id, Principal principal) {
        ResponseEntity<?> errorResponseEntity = mapValidationErrorService.getErrorResponseEntity(bindingResult);
        if (errorResponseEntity != null)
            return errorResponseEntity;
        else {
            projectTask = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());
            return new ResponseEntity<>(projectTask, HttpStatus.CREATED);
        }
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
        return projectTaskService.findBacklogById(backlog_id, principal.getName());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, pt_id, principal.getName());
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
                                               @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        ResponseEntity<?> errorResponseEntity = mapValidationErrorService.getErrorResponseEntity(bindingResult);
        if (errorResponseEntity != null)
            return errorResponseEntity;
        else {
            projectTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id, principal.getName());
            return new ResponseEntity<>(projectTask, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        projectTaskService.deleteByProjectSequence(backlog_id, pt_id, principal.getName());
        return new ResponseEntity<>("ProjectTask with ID " + pt_id + " was deleted", HttpStatus.OK);

    }
}
