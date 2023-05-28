package com.rbaliwal00.todoappusingjsp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="community")
public class Community extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> members = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> requestingMembers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User admin;

//    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
//    private List<Issue> issues = new ArrayList<>();

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
