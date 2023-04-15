package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.Issue;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.repository.CommentRepository;
import com.rbaliwal00.todoappusingjsp.repository.IssueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(IssueRepository issueRepository,ModelMapper modelMapper, CommentRepository commentRepository) {
        this.issueRepository = issueRepository;
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto addComment(User user, Long issueId, CommentDto commentDto) throws Exception {
        Comment comment = new Comment();
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new Exception("Issue Not Found"));

        comment.setId(commentDto.getId());
        comment.setIssue(issue);
        comment.setContent(commentDto.getContent());
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public Set<Comment> getCommentsByIssueId(Long issueId) {
        Set<Comment> comments = commentRepository.findByIssueId(issueId);
        return comments;
    }
}
