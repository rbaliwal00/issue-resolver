package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.CommentDto;
import com.rbaliwal00.todoappusingjsp.dto.CommentResponse;
import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.Issue;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.repository.CommentRepository;
import com.rbaliwal00.todoappusingjsp.repository.IssueRepository;
import com.rbaliwal00.todoappusingjsp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(IssueRepository issueRepository, ModelMapper modelMapper, CommentRepository commentRepository, UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDto addComment(Long userId, Long issueId, CommentDto commentDto) throws Exception {
        Comment comment = new Comment();
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new Exception("Issue Not Found"));

        comment.setId(commentDto.getId());
        comment.setIssue(issue);
        comment.setContent(commentDto.getContent());

        User user = userRepository.findById(userId).orElseThrow();
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public Set<Comment> getCommentsByIssueId(Long issueId) {
        Set<Comment> comments = commentRepository.findByIssueId(issueId);
        return comments;
    }

    @Override
    public CommentResponse getPaginatedCommentsByIssueId(Long issueId, Integer pageNumber, Integer pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Comment> comments = commentRepository.findAll(p);

        List<Comment> content = comments.getContent();

        List<CommentDto> list = new ArrayList<>();
        for(Comment i: content){
            if(i.getIssue().getId() == issueId) {
                list.add(modelMapper.map(i, CommentDto.class));
            }
        }
        System.out.println(issueId);
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(list);
        commentResponse.setPageNumber(comments.getNumber());
        commentResponse.setPageSize(comments.getSize());
        commentResponse.setTotalElements(list.size());
        commentResponse.setTotalPages(comments.getTotalPages());
        commentResponse.setLastPage(comments.isLast());

        return commentResponse;
    }
}
