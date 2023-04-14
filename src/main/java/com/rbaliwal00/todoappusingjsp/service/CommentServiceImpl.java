package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.dto.IssueDto;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.Issue;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.repository.CommentRepository;
import com.rbaliwal00.todoappusingjsp.repository.IssueRepository;
import com.rbaliwal00.todoappusingjsp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final IssueRepository issueRepository;
    private final IssueService issueService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(IssueRepository issueRepository, IssueService issueService, ModelMapper modelMapper, UserService userService, UserRepository userRepository, CommentRepository commentRepository) {
        this.issueRepository = issueRepository;
        this.issueService = issueService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto addComment(Long userId, Long issueId, CommentDto commentDto) throws Exception {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new Exception("Issue Not Found"));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User Not Found"));
        comment.setUser(user);
        comment.setIssue(issue);
        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public List<Comment> getAllCommentsByIssueId(Long issueId) {
        System.out.println(commentRepository.findAllByIssueId(issueId));
        return commentRepository.findAllByIssueId(issueId);
    }
}
