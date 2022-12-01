package com.bugtracker.bugtracker.controller;

import com.bugtracker.bugtracker.dto.AddMemberForProjectOrTicket;
import com.bugtracker.bugtracker.dto.ProjectDto;
import com.bugtracker.bugtracker.dto.TicketDto;
import com.bugtracker.bugtracker.entity.Member;
import com.bugtracker.bugtracker.entity.Project;
import com.bugtracker.bugtracker.entity.Ticket;
import com.bugtracker.bugtracker.repository.MemberRepository;
import com.bugtracker.bugtracker.repository.ProjectRepository;
import com.bugtracker.bugtracker.service.MemberService;
import com.bugtracker.bugtracker.service.ProjectService;
import com.bugtracker.bugtracker.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ProjectController {

    private final MemberService memberService;
    private final TicketService ticketService;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    public ProjectController( MemberService memberService, TicketService ticketService, ProjectRepository projectRepository, ProjectService projectService) {
        this.memberService = memberService;
        this.ticketService = ticketService;
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    @GetMapping("/members/{mid}")
    public ResponseEntity<Member> getMember(@PathVariable(name = "mid")int id){
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @PostMapping("members/{mid}/projects")
    public ResponseEntity<?> createProject(@RequestBody ProjectDto projectDto) {
        projectService.createProject(projectDto);
        return new ResponseEntity<>("Project created", HttpStatus.CREATED);
    }


    @GetMapping("members/{mid}/projects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
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


    @PostMapping("/members/{mid}/projects/{pid}/tickets")
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketDto ticketDto, @PathVariable(name = "pid") int id) {
        ticketService.createTicket(ticketDto, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/members/{mid}/projects/{pid}/tickets")
    public List<Ticket> getAllTicketsByProjectId(@PathVariable(name = "pid") int id) {
        return ticketService.getAllTicketsForProject(id);
    }

    @GetMapping("/members/{mid}/projects/{pid}/tickets/{tid}")
    public ResponseEntity<Ticket> getTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid) {
        return new ResponseEntity<>(ticketService.getTicketById(id, tid), HttpStatus.OK);
    }

    @PatchMapping("/members/{mid}/projects/{pid}/tickets/{tid}")
    public ResponseEntity<?> updateTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid, @RequestBody TicketDto ticketDto) {
        ticketService.editTicket(ticketDto, tid, id);
        return new ResponseEntity<>("ticket updated", HttpStatus.OK);
    }


    @DeleteMapping("/members/{mid}/projects/{pid}/tickets/{tid}")
    public ResponseEntity<String> deleteTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid) {
        ticketService.deleteTicketById(id, tid);
        return new ResponseEntity<>("ticket deleted successfully.", HttpStatus.OK);
    }

    @PostMapping("/members/{mid}/projects/{pid}/tickets/{tid}/add")
    public ResponseEntity<?> assignMemberToTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid, @RequestBody AddMemberForProjectOrTicket members) {
        ticketService.AddMemberToAssignedDevsForTicket(members, id, tid);
        return new ResponseEntity<>("assigned", HttpStatus.OK);
    }


    @PostMapping("/members/{mid}/projects/{pid}/tickets/{tid}/remove")
    public ResponseEntity<?> removeMemberToTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid, @RequestBody AddMemberForProjectOrTicket members) {
        ticketService.removeMemberToAssignedDevsForTicket(members, id, tid);
        return new ResponseEntity<>("removed", HttpStatus.OK);
    }

}
