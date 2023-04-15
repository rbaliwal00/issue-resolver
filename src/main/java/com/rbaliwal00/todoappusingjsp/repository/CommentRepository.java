package com.rbaliwal00.todoappusingjsp.repository;

import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c "
            + " where c.issue.id = :issueId")
    Set<Comment> findByIssueId(Long issueId);
}
