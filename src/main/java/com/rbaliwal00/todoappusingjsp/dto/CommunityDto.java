package com.rbaliwal00.todoappusingjsp.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CommunityDto {
    private Long id;
    private String name;
    private String description;
    private UserDto admin;
    private List<UserDto> requestingUsers = new ArrayList<>();
    private List<UserDto> members = new ArrayList<>();
}
