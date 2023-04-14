package com.rbaliwal00.todoappusingjsp.controller;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.service.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping( "/issues/{issueId}/comment")
    public List<Comment> getAllCommentsForIssue(@PathVariable (value = "issueId") Long issueId,
                                                Pageable pageable) {
        return commentService.getAllCommentsByIssueId(issueId);
    }
//
    @PostMapping( "/users/{userId}/issues/{issueId}/comment")
    public ResponseEntity<CommentDto> addCommentToIssue(@PathVariable Long userId, @PathVariable Long issueId,
                                            @RequestBody CommentDto comment) throws Exception {
        CommentDto commentCreated = commentService.addComment(userId, issueId, comment);
        return new ResponseEntity<CommentDto>(commentCreated, HttpStatus.CREATED);
    }
}
