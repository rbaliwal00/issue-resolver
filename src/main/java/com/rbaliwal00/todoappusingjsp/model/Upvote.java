package com.rbaliwal00.todoappusingjsp.model;

import jakarta.persistence.*;

public class Upvote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;
}
