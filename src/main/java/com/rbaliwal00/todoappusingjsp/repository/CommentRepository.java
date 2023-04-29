package com.rbaliwal00.todoappusingjsp.repository;

import com.rbaliwal00.todoappusingjsp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c "
            + " where c.issue.id = :issueId")
    Set<Comment> findByIssueId(Long issueId);
}
