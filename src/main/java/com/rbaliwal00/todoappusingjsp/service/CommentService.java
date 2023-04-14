package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    CommentDto addComment(Long userId, Long issueId, CommentDto comment) throws Exception;
//
    List<Comment> getAllCommentsByIssueId(Long postId);
//    void updateComment();
//    void deleteComment();
//    void Comment();
//    List<Comment> findByIssueId();
}
