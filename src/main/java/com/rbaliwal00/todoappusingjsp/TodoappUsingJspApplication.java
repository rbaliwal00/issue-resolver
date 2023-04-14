package com.rbaliwal00.todoappusingjsp;

import com.rbaliwal00.todoappusingjsp.model.Comment;
import com.rbaliwal00.todoappusingjsp.model.Issue;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.repository.IssueRepository;
import com.rbaliwal00.todoappusingjsp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoappUsingJspApplication{

    public static void main(String[] args) {
        SpringApplication.run(TodoappUsingJspApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
