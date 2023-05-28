package com.rbaliwal00.todoappusingjsp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@JsonIgnoreProperties({"comments"})
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "first name should not be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "last name should not be empty")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Not a valid email")
    private String email;

    @NotEmpty
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$",
//            message ="1 Uppercase, 1 Lowercase, 1 special character, 1 number, Min 8 characters ")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany(mappedBy = "user",
//            cascade = CascadeType.ALL)
//    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(mappedBy = "assignees", fetch = FetchType.LAZY)
    private List<Issue> issues = new ArrayList<>();

    @ManyToMany(mappedBy = "voters")
    private List<Issue> votedIssues = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    private List<Community> communities = new ArrayList<>();

    @ManyToMany(mappedBy = "requestingMembers")
    private List<Community> requestedCommunities = new ArrayList<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Community> communitiesCreated = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
