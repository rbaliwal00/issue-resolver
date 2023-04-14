package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.IssueDto;
import com.rbaliwal00.todoappusingjsp.model.Issue;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.repository.IssueRepository;
import com.rbaliwal00.todoappusingjsp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    public IssueServiceImpl(IssueRepository issueRepository, ModelMapper modelMapper, UserRepository userRepository, UserService userService) {
        this.issueRepository = issueRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public List<IssueDto> findByUser(Long userId) throws Exception {
//        try{
//            User user = userRepository.findById(userId).orElseThrow(() -> new Exception());
//
//            issues = issueRepository.findByUser(user);
//            result = issues.stream().map((issue)-> modelMapper.map(issue, IssueDto.class));
////                    .stream().map(
////                            issue -> new IssueDto(issue.getId(), issue.getTitle(), issue.getDescription(), issue.getDateCreated(), issue.isOpen(), userService.convertEntityToDto(issue.getUser()), issue.getComments()))
////                    .collect(Collectors.toList());
//
//
//        }catch (Exception ex){
//            System.out.println(ex);
//        }
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception());
        List<Issue> issues = issueRepository.findByUser(user);
        List<IssueDto> result = issues.stream().map((issue)-> modelMapper.map(issue, IssueDto.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public IssueDto findById(Long userId, Long id) throws Exception {
        Issue issue = issueRepository.findById(id).orElseThrow();
        if(issue == null){
            throw new NoSuchElementException();
        }
        if(!issue.getUser().getId().equals(userId)){
            throw new Exception("Unauthorised Attempt");
        }
        return modelMapper.map(issue, IssueDto.class);
    }

    @Override
    public IssueDto findById(Long id) throws Exception {
        Issue issue = issueRepository.findById(id).orElseThrow();
        if(issue == null){
            throw new NoSuchElementException();
        }
        return modelMapper.map(issue, IssueDto.class);
    }

    @Override
    public void addIssue(Long userId, IssueDto dto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception());
        Issue issue = modelMapper.map(dto, Issue.class);
        issue.setUser(user);
        issue.setDateCreated(LocalDate.now());
        issueRepository.save(issue);
    }

    @Override
    public ResponseEntity<Void> deleteById(Long userId, Long id) throws Exception {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new Exception());
        if(!issue.getUser().getId().equals(userId)){
            throw new Exception("Unauthorised Attempt");
        }
        issueRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void updateIssue(Long userId, IssueDto dto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception());
        if(dto==null){
            throw new NoSuchElementException();
        }
        deleteById(userId, dto.getId());
        Issue issue = modelMapper.map(dto, Issue.class);
        issue.setUser(user);
        issueRepository.save(issue);
    }

    @Override
    public List<IssueDto> findAll() {
        List<Issue> temp = issueRepository.findAll();
        List<IssueDto> list = new ArrayList<>();
        for(Issue i: temp){
            list.add(modelMapper.map(i, IssueDto.class));
        }
        return list;
    }
}
