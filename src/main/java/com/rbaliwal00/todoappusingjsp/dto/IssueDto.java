package com.rbaliwal00.todoappusingjsp.dto;

import com.rbaliwal00.todoappusingjsp.model.Comment;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
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

    private Set<CommentDto> comments = new HashSet<>();
}
