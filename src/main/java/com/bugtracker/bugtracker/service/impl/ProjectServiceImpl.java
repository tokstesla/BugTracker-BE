package com.bugtracker.bugtracker.service.impl;

import com.bugtracker.bugtracker.dto.AddMemberForProjectOrTicket;
import com.bugtracker.bugtracker.dto.ProjectDto;
import com.bugtracker.bugtracker.entity.Member;
import com.bugtracker.bugtracker.entity.Project;
import com.bugtracker.bugtracker.exception.ResourceNotFoundException;
import com.bugtracker.bugtracker.repository.MemberRepository;
import com.bugtracker.bugtracker.repository.ProjectRepository;
import com.bugtracker.bugtracker.service.ProjectService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;


    public ProjectServiceImpl(ProjectRepository projectRepository, MemberRepository memberRepository) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void createProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        List<Member> members = projectDto.getMembers().stream().map(member -> memberRepository.findMemberById(member)).toList();
        project.setMembers(members);
        projectRepository.save(project);
    }

    @Override
    public Project editProject(ProjectDto projectDto, int projectId) {
        Project project = projectRepository.findProjectById(projectId);
        if (projectDto.getName() != null && !projectDto.getName().isEmpty()) {
            project.setName(projectDto.getName());
        }
        if (projectDto.getDescription() != null && !projectDto.getDescription().isEmpty()) {
            project.setDescription(projectDto.getDescription());
        }
        if (projectDto.getMembers() != null && !projectDto.getMembers().isEmpty()) {
            List<Member> contributors = projectDto.getMembers().stream().map(member -> memberRepository.findMemberById(member)).toList();
            project.setMembers(contributors);
        }
        projectRepository.save(project);
        return project;
    }

    @Override
    public Project getProjectById(int projectId) {
        return projectRepository.findProjectById(projectId);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }


    @Override
    public void addMemberToProjectTeam(AddMemberForProjectOrTicket addMemberForProjectOrTicket, int projectId) {
        List<Member> members = addMemberForProjectOrTicket.getMembers().stream().map(id -> memberRepository.findMemberById(id)).toList();
        Project project = projectRepository.findProjectById(projectId);
        project.setMembers(members);
        projectRepository.save(project);
    }

    @Override
    public void removeMemberFromProjectTeam(AddMemberForProjectOrTicket addMemberForProjectOrTicket, int projectId) {
        List<Member> ids = addMemberForProjectOrTicket.getMembers().stream().map(input ->
                memberRepository.findMemberById(input)).toList();

        List<Member> members = projectRepository.findProjectById(projectId).getMembers().stream().filter(member -> !ids.contains(member)).toList();
        projectRepository.findProjectById(projectId).setMembers(members);
    }


    @Override
    public void deleteProjectById(int id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("project not found"));
        projectRepository.delete(project);
    }

    private Integer convertToInteger(String num) {
        return Integer.getInteger(num);
    }
}
