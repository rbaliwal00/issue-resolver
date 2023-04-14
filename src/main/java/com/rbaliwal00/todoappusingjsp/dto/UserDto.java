package com.rbaliwal00.todoappusingjsp.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
}
