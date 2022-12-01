package com.bugtracker.bugtracker.service;

import com.bugtracker.bugtracker.dto.AddMemberForProjectOrTicket;
import com.bugtracker.bugtracker.dto.ProjectDto;
import com.bugtracker.bugtracker.dto.ProjectResponse;
import com.bugtracker.bugtracker.entity.Project;

import java.util.List;

public interface ProjectService {
    void createProject(ProjectDto projectDto);
    Project editProject(ProjectDto projectDto,int projectId);

    Project getProjectById(int projectId);


    List<Project> getAllProjects();

    void addMemberToProjectTeam(AddMemberForProjectOrTicket addMemberForProjectOrTicket , int productId);

    void removeMemberFromProjectTeam(AddMemberForProjectOrTicket addMemberForProjectOrTicket , int projectId);

    void deleteProjectById(int id);
}
