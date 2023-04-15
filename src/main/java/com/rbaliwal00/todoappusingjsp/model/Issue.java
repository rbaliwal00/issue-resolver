package com.rbaliwal00.todoappusingjsp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name="issue")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private LocalDate dateCreated;
    private boolean isOpen;

    @ManyToOne
    private User user;

//    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
//    private Set<Comment> comments = new HashSet<>();
}
