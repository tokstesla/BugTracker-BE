package com.bugtracker.bugtracker.repository;

import com.bugtracker.bugtracker.entity.Project;
import com.bugtracker.bugtracker.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProjectRepository extends  JpaRepository<Project,Integer>{

    Optional<Project> findById(int projectId);
    Project findProjectById(int id);

}
