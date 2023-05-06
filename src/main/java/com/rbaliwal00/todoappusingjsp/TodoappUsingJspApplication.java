package com.rbaliwal00.todoappusingjsp;

import com.rbaliwal00.todoappusingjsp.repository.IssueRepository;
import com.rbaliwal00.todoappusingjsp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoappUsingJspApplication{
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public TodoappUsingJspApplication(IssueRepository issueRepository, UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TodoappUsingJspApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
