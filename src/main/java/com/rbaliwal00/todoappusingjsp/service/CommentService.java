package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.User;

import java.util.Set;

public interface CommentService {
    CommentDto addComment(User user, Long issueId, CommentDto comment) throws Exception;

    Set<Comment> getCommentsByIssueId(Long issueId);
}
