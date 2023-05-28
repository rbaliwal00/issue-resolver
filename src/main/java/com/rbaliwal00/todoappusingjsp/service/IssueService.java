package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.IssueDto;
import com.rbaliwal00.todoappusingjsp.dto.IssueResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IssueService {
    List<IssueDto> findByUser(Long userId) throws Exception;
    IssueDto findById(Long userId, Long id) throws Exception;
    void addIssue(Long userId, IssueDto dto) throws Exception;
    ResponseEntity<Void> deleteById(Long userId, Long id) throws Exception;
    void updateIssue(Long userId, IssueDto dto) throws Exception;
    IssueResponse findAll(Integer pageNumber, Integer pageSize, String keyword);
    IssueDto findById(Long id) throws Exception;
    IssueDto assignIssue(Long issueId, String userEmail);
    IssueDto closeIssue(Long issueId);
    IssueDto upvote(Long issueId, Long userId);
    List<IssueDto> getHomeIssues() throws Exception;
    List<IssueDto> getAssignedIssues(Long userId) throws Exception;
}
