package com.rbaliwal00.todoappusingjsp.repository;

import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByIssueId(Long issueId);
}
