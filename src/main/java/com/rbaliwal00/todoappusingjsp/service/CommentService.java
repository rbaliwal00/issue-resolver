package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.dto.CommentResponse;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.User;

import java.util.Set;

public interface CommentService {
    CommentDto addComment(Long userId, Long issueId, CommentDto comment) throws Exception;

    Set<Comment> getCommentsByIssueId(Long issueId);

    CommentResponse getPaginatedCommentsByIssueId(Long issueId, Integer pageNumber, Integer pageSize);
}
