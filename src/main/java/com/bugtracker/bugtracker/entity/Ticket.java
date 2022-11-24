package com.bugtracker.bugtracker.entity;

import com.bugtracker.bugtracker.entity.enums.Priority;
import com.bugtracker.bugtracker.entity.enums.Status;
import com.bugtracker.bugtracker.entity.enums.Type;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Ticket extends BaseEntity {

    private String title;

    private String description;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Member author;

    private Status status;

    private Date date;

    private Priority priority;

    private Type type;

    @OneToMany(mappedBy = "ticket")
    private List<Member> assigned_devs;

    @OneToMany(mappedBy = "ticket")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;
}
