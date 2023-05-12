package com.rbaliwal00.todoappusingjsp.controller;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.dto.CommentResponse;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.repository.CommentRepository;
import com.rbaliwal00.todoappusingjsp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    public CommentController(CommentService commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    @GetMapping( "/comments")
    public List<Comment> getAllCommentsForIssue() {
        return commentRepository.findAll();
    }

    @GetMapping( "/comments/paged/{issueId}")
    public CommentResponse getAllPagedCommentsForIssue(@PathVariable Long issueId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        return commentService.getPaginatedCommentsByIssueId(issueId,pageNumber, pageSize);
    }

    @PostMapping( "/users/{userId}/issues/{issueId}/comment")
    public ResponseEntity<CommentDto> addCommentToIssue(@PathVariable Long userId, @PathVariable Long issueId,
                                                        @RequestBody CommentDto comment) throws Exception {
        CommentDto commentCreated = commentService.addComment(userId, issueId, comment);
        return new ResponseEntity<CommentDto>(commentCreated, HttpStatus.CREATED);
    }

    @GetMapping("/comments/{issueId}")
    public ResponseEntity<Set<Comment>> getCommentsByIssue(@PathVariable Long issueId) {
        Set<Comment> comments = commentService.getCommentsByIssueId(issueId);

        return ResponseEntity.ok(comments);
    }
}
