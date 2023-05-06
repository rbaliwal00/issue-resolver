package com.rbaliwal00.todoappusingjsp.controller;

import com.rbaliwal00.todoappusingjsp.dto.IssueDto;
import com.rbaliwal00.todoappusingjsp.dto.IssueResponse;
import com.rbaliwal00.todoappusingjsp.dto.UserDto;
import com.rbaliwal00.todoappusingjsp.model.Issue;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.repository.UserRepository;
import com.rbaliwal00.todoappusingjsp.service.CommentService;
import com.rbaliwal00.todoappusingjsp.service.IssueService;
import com.rbaliwal00.todoappusingjsp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IssueController {

    private final IssueService issueService;
    private final UserService userService;

    public IssueController(IssueService issueService, UserService userService) {
        this.issueService = issueService;
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{userId}/issues",method = RequestMethod.GET)
    public List<IssueDto> listAllIssues(@PathVariable Long userId) throws Exception {
        return issueService.findByUser(userId);
    }

    @RequestMapping(value = "/issues",method = RequestMethod.GET)
    public IssueResponse listAllIssues(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return issueService.findAll(pageNumber, pageSize, keyword);
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

    @GetMapping("/experts")
    public List<UserDto> getAllExperts(){
        return userService.getAllExperts();
    }

    @PostMapping( "/upvote/user/{userId}/issues/{issueId}")
    public void upvote(@PathVariable Long issueId,
                       @PathVariable Long userId) throws Exception {
        issueService.upvote(issueId, userId);
    }

    @PostMapping( "/users/{email}/issues/{issueId}/assignee")
    public IssueDto assignIssue(@PathVariable Long issueId,
                            @PathVariable String email) throws Exception {
        return issueService.assignIssue(issueId, email);
    }

    @PostMapping( "/users/issues/{issueId}/close_issue")
    public IssueDto closingIssue(@PathVariable Long issueId) throws Exception {
        return issueService.closeIssue(issueId);
    }

    @GetMapping("/issues/home_issues")
    public List<IssueDto> getHomeIssues() throws Exception {
        return issueService.getHomeIssues();
    }

    @GetMapping("/users/{userId}/issues/assigned_issues")
    public List<IssueDto> getAssignedIssues( @PathVariable Long userId) throws Exception {
        return issueService.getAssignedIssues(userId);
    }

}
