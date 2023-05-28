package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.dto.IssueDto;
import com.rbaliwal00.todoappusingjsp.dto.IssueResponse;
import com.rbaliwal00.todoappusingjsp.dto.UserDto;
import com.rbaliwal00.todoappusingjsp.exception.ResourceNotFoundException;
import com.rbaliwal00.todoappusingjsp.model.*;
import com.rbaliwal00.todoappusingjsp.repository.CommentRepository;
import com.rbaliwal00.todoappusingjsp.repository.IssueRepository;
import com.rbaliwal00.todoappusingjsp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public IssueServiceImpl(IssueRepository issueRepository, ModelMapper modelMapper, UserRepository userRepository, UserService userService, CommentService commentService, CommentRepository commentRepository) {
        this.issueRepository = issueRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public Issue issueDtoToIssue(IssueDto dto){
        Issue issue = issueRepository.
                findById(dto.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Issue", " Id ", dto.getId()));
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        Issue save = issueRepository.save(issue);
        return save;
    }

    public IssueDto issueToIssueDto(Issue issue){
        IssueDto dto = new IssueDto();
        dto.setId(issue.getId());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        dto.setUser(modelMapper.map(issue.getUser(), UserDto.class));
        dto.setOpen(issue.isOpen());
        dto.setDateCreated(issue.getDateCreated());

        List<Comment> comments = issue.getComments();
        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment c: comments){
            CommentDto temp = new CommentDto();
            temp.setId(c.getId());
            temp.setContent(c.getContent());
            temp.setUser(modelMapper.map(c.getUser(), UserDto.class));
            commentDtos.add(temp);
        }
        dto.setComments(commentDtos);

        List<User> votesList = issue.getVoters();
        List<UserDto> votes = new ArrayList<>();
        for(User u : votesList){
            votes.add(modelMapper.map(u, UserDto.class));
        }
        dto.setVotes(votes);

        List<User> list = issue.getAssignees();
        List<UserDto> userDtos = new ArrayList<>();
        for(User u : list){
            userDtos.add(modelMapper.map(u, UserDto.class));
        }
        dto.setAssignees(userDtos);

        return dto;
    }

    @Override
    public List<IssueDto> findByUser(Long userId) throws Exception {
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

        return issueToIssueDto(issue);
    }

    @Override
    public IssueDto findById(Long id) throws Exception {
        Issue issue = issueRepository.findById(id).orElseThrow();
        if(issue == null){
            throw new NoSuchElementException();
        }
        return issueToIssueDto(issue);
    }

    @Override
    public IssueDto assignIssue(Long issueId, String userEmail) {
        Issue issue = issueRepository.findById(issueId).orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(issue.getAssignees().contains(user)){
            return modelMapper.map(issue, IssueDto.class);
        }
        issue.getAssignees().add(user);
        user.getIssues().add(issue);
        Issue i = issueRepository.save(issue);
        return modelMapper.map(i, IssueDto.class);
    }

    @Override
    public IssueDto closeIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow();
        issue.setOpen(false);
        issueRepository.save(issue);
        return issueToIssueDto(issue);
    }

    @Override
    public IssueDto upvote(Long issueId, Long userId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        if(issue.getVoters().contains(user)){
            issue.getVoters().remove(user);
        }else{
            issue.getVoters().add(user);
        }
        Issue save = issueRepository.save(issue);

        return issueToIssueDto(save);

    }

    @Override
    public List<IssueDto> getHomeIssues() throws Exception {
        List<Issue> all = issueRepository.findAllSortedByLikesAndComments();
//        Map<Integer, Integer> map = new HashMap<>();
//        List<IssueDto> result = new ArrayList<>();
//        for(int i = 0; i < 3; i++){
//            int max = -1;
//            Issue issue = all.get(0);
//            for(Issue issue1: all){
//                if(max == issue1.getVoters().size()){
//                    if(issue.getComments().size() < issue1.getComments().size()){
//                        issue = issue1;
//                    }
//                }
//                else if(max < issue1.getVoters().size()) {
//                    max = issue1.getVoters().size();
//                    issue = issue1;
//                }
//            }
//            all.remove(issue);
//            result.add(findById(issue.getId()));
//        }
        List<IssueDto> issueDtos = new ArrayList<>();
        int count = 0;
        for(Issue i : all){
            if(count > 2){
                break;
            }
            count++;
            issueDtos.add(issueToIssueDto(i));
        }
        return issueDtos;
    }

    @Override
    public List<IssueDto> getAssignedIssues(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        List<Issue> issues = user.getIssues();
        List<IssueDto> assignedIssues = new ArrayList<>();
        for(Issue i: issues){
            assignedIssues.add(issueToIssueDto(i));
        }
        return assignedIssues;
    }

    @Override
    public void addIssue(Long userId, IssueDto dto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception());
        Issue issue = new Issue();
        issue.setUser(user);
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setDateCreated(LocalDate.now());
        issue.setOpen(true);
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
        Issue issue = issueRepository.findById(dto.getId()).orElseThrow();
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issueRepository.save(issue);
//        deleteById(userId, issue.getId());
    }

    @Override
    public IssueResponse findAll(Integer pageNumber, Integer pageSize, String keyword) {

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Issue> pageIssue = issueRepository.findByTitleContaining(keyword,p);
        List<Issue> content = pageIssue.getContent();
        List<IssueDto> list = content.stream().map(issue ->
            IssueDto.builder()
                    .id(issue.getId())
                    .title(issue.getTitle())
                    .username(issue.getUser().getEmail())
                    .dateCreated(issue.getDateCreated())
                    .build()
        ).collect(Collectors.toList());

        IssueResponse issueResponse = new IssueResponse();
        issueResponse.setContent(list);
        issueResponse.setPageNumber(pageIssue.getNumber());
        issueResponse.setPageSize(pageIssue.getSize());
        issueResponse.setTotalElements(pageIssue.getTotalElements());
        issueResponse.setTotalPages(pageIssue.getTotalPages());
        issueResponse.setLastPage(pageIssue.isLast());
        return issueResponse;
    }
}
