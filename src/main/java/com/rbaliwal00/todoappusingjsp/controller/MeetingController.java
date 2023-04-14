package com.rbaliwal00.todoappusingjsp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/meeting")
public class MeetingController {

    @GetMapping
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
