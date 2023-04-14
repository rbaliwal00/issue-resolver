package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.IssueDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IssueService {
    List<IssueDto> findByUser(Long userId) throws Exception;
    IssueDto findById(Long userId, Long id) throws Exception;
    void addIssue(Long userId, IssueDto dto) throws Exception;
    ResponseEntity<Void> deleteById(Long userId, Long id) throws Exception;
    void updateIssue(Long userId, IssueDto dto) throws Exception;

    List<IssueDto> findAll();
    IssueDto findById(Long id) throws Exception;
}
