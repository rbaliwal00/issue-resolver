package com.rbaliwal00.todoappusingjsp.repository;

import com.rbaliwal00.todoappusingjsp.model.Issue;
import com.rbaliwal00.todoappusingjsp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {
    List<Issue> findByUser(User user);
    Page<Issue> findByTitleContaining(String keyword, Pageable p);
}
