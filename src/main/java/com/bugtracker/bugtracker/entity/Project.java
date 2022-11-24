package com.bugtracker.bugtracker.entity;

import com.bugtracker.bugtracker.dto.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Project extends BaseEntity {

    private String name;

    private String description;

    @OneToMany(mappedBy = "project")
    private List<Member> members;

    @OneToMany(mappedBy = "project")
    private List<Ticket> tickets;

}
