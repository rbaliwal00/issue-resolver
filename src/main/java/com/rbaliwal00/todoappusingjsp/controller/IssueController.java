package com.rbaliwal00.todoappusingjsp.controller;

import com.rbaliwal00.todoappusingjsp.dto.IssueDto;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.service.CommentService;
import com.rbaliwal00.todoappusingjsp.service.IssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IssueController {

    private final IssueService issueService;
    private final CommentService commentService;

    public IssueController(IssueService issueService, CommentService commentService) {
        this.issueService = issueService;
        this.commentService = commentService;
    }

    @RequestMapping(value = "/users/{userId}/issues",method = RequestMethod.GET)
    public List<IssueDto> listAllIssues(@PathVariable Long userId) throws Exception {
        return issueService.findByUser(userId);
    }

    @RequestMapping(value = "/issues",method = RequestMethod.GET)
    public List<IssueDto> listAllIssues(){
        return issueService.findAll();
    }

    @RequestMapping(value = "/users/{userId}/issues/{id}",method = RequestMethod.GET)
    public IssueDto listIssue(@PathVariable Long userId, @PathVariable Long id) throws Exception {
        return issueService.findById(userId, id);
    }

    @RequestMapping(value = "/issues/{id}",method = RequestMethod.GET)
    public IssueDto issue(@PathVariable Long id) throws Exception {
        return issueService.findById(id);
    }

    @DeleteMapping("/users/{userId}/issues/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long userId, @PathVariable Long id) throws Exception {
        return issueService.deleteById(userId, id);
    }

    @PutMapping( "/users/{userId}/issues")
    public void updateTodo(@PathVariable Long userId, @RequestBody IssueDto issueDto) throws Exception {
        issueService.updateIssue(userId, issueDto);
    }

    @PostMapping( "/users/{userId}/issues")
    public void createIssue(@PathVariable Long userId,
                           @RequestBody IssueDto issueDto) throws Exception {
        issueService.addIssue(userId, issueDto);
    }
}
