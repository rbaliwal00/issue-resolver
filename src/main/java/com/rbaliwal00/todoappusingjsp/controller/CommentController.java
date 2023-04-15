package com.rbaliwal00.todoappusingjsp.controller;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.repository.CommentRepository;
import com.rbaliwal00.todoappusingjsp.service.CommentService;
import org.springframework.data.domain.Pageable;
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
//
    @PostMapping( "/users/issues/{issueId}/comment")
    public ResponseEntity<CommentDto> addCommentToIssue(@AuthenticationPrincipal User user, @PathVariable Long issueId,
                                                        @RequestBody CommentDto comment) throws Exception {
        CommentDto commentCreated = commentService.addComment(user, issueId, comment);
        return new ResponseEntity<CommentDto>(commentCreated, HttpStatus.CREATED);
    }

    @GetMapping("/comments/{issueId}")
    public ResponseEntity<Set<Comment>> getCommentsByIssue(@PathVariable Long issueId) {
        Set<Comment> comments = commentService.getCommentsByIssueId(issueId);

        return ResponseEntity.ok(comments);
    }
}
