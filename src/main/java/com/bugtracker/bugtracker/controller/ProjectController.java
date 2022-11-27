package com.bugtracker.bugtracker.controller;

import com.bugtracker.bugtracker.dto.AddMemberForProjectOrTicket;
import com.bugtracker.bugtracker.dto.ProjectDto;
import com.bugtracker.bugtracker.dto.ProjectResponse;
import com.bugtracker.bugtracker.entity.Project;
import com.bugtracker.bugtracker.repository.MemberRepository;
import com.bugtracker.bugtracker.repository.ProjectRepository;
import com.bugtracker.bugtracker.service.ProjectService;
import com.bugtracker.bugtracker.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ProjectController {
    private final AuthenticationManager authenticationManager;

    private final MemberRepository memberRepository;

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    public ProjectController(AuthenticationManager authenticationManager, MemberRepository memberRepository, ProjectRepository projectRepository, ProjectService projectService) {
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }


    @PostMapping("members/{mid}/projects")
    public ResponseEntity<?> createProject(@RequestBody ProjectDto projectDto) {
        projectService.createProject(projectDto);
        return new ResponseEntity<>("Project created", HttpStatus.CREATED);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("members/{mid}/projects")
    public ProjectResponse getAllProjects(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return projectService.getAllProjects(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("members/{mid}/projects/{pid}")
    public ResponseEntity<Project> getProjectById(@PathVariable(name = "pid") int id) {
        return new ResponseEntity<Project>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @PutMapping("members/{mid}/projects/{pid}")
    public ResponseEntity<Project> updateProject(@Valid @RequestBody ProjectDto project, @PathVariable(name = "pid") int id) {
        Project projectResponse = projectService.editProject(project, id);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @DeleteMapping("members/{mid}/projects/{pid}")
    public ResponseEntity<String> deleteProject(@PathVariable(name = "pid") int id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>("Project entity deleted successfully.", HttpStatus.OK);
    }

    @PostMapping("members/{mid}/projects/{pid}/")
    public ResponseEntity<?> assignMemberToProject(@PathVariable(name = "pid") int id, @RequestBody AddMemberForProjectOrTicket members) {
        projectService.addMemberToProjectTeam(members, id);
        return new ResponseEntity<>("assigned", HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("members/{mid}/projects/")
    public List<Project> getProjectsByMemberId(@PathVariable(name = "mid") int id) {
        return projectRepository.findAll().stream().filter(project -> project.getMembers().stream().filter(member -> member.getId().equals(id)).isParallel()).toList();
    }

    @PostMapping("members/{mid}/projects/{pid}")
    public ResponseEntity<?> removeMemberToProject(@PathVariable(name = "pid") int id, @RequestBody AddMemberForProjectOrTicket members) {
        projectService.removeMemberFromProjectTeam(members, id);
        return new ResponseEntity<>("removed", HttpStatus.OK);
    }

}
