package com.rbaliwal00.todoappusingjsp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name="community")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> members = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> requestingMembers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User admin;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private Set<Issue> issues = new HashSet<>();
}
