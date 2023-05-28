package com.rbaliwal00.todoappusingjsp.dto;

import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.User;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate dateCreated;
    private boolean isOpen;
    private UserDto user;
    private String username;
    private List<CommentDto> comments = new ArrayList<>();
    private List<UserDto> assignees = new ArrayList<>();
    private List<UserDto> votes = new ArrayList<>();

}
