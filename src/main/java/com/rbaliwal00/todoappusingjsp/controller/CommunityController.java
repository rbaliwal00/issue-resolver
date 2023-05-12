package com.rbaliwal00.todoappusingjsp.controller;

import com.rbaliwal00.todoappusingjsp.dto.ApiResponse;
import com.rbaliwal00.todoappusingjsp.dto.CommunityDto;
import com.rbaliwal00.todoappusingjsp.dto.CommunityResponse;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping( "/community/creating_community")
    public ResponseEntity<CommunityDto> creatingCommunity(@AuthenticationPrincipal User user,
                                                        @RequestBody CommunityDto communityDto) throws Exception {
        CommunityDto communityCreated = communityService.createCommunity(user, communityDto);
        return new ResponseEntity<CommunityDto>(communityCreated, HttpStatus.CREATED);
    }

    @GetMapping( "/community/communities")
    public CommunityResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return communityService.findAll(pageNumber,pageSize,keyword);
    }

    @GetMapping( "/community/communities/{communityId}")
    public CommunityDto findAll(@PathVariable Long communityId){
        return communityService.findById(communityId);
    }

    @PostMapping( "/community/request/{communityId}/user/{userId}")
    public CommunityDto userRequestingCommunityApi(@PathVariable Long userId,
                                                @PathVariable Long communityId) throws Exception {
        return communityService.userRequestingCommunity(userId, communityId);
    }
}
