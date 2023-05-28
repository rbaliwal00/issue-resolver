package com.rbaliwal00.todoappusingjsp.controller;

import com.rbaliwal00.todoappusingjsp.dto.APIResponse;
import com.rbaliwal00.todoappusingjsp.dto.CommunityDto;
import com.rbaliwal00.todoappusingjsp.dto.CommunityResponse;
import com.rbaliwal00.todoappusingjsp.dto.UserDto;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.log4j2.SpringBootConfigurationFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import java.util.List;

@RestController
@Slf4j
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

    @GetMapping( "/community/communities/user/{userId}")
    public ResponseEntity<APIResponse> findCommunityByUser(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
            @PathVariable Long userId)
            {
        CommunityResponse byUser = communityService.findByUser(pageNumber,pageSize,keyword,userId);
        APIResponse<CommunityResponse> apiResponse = APIResponse.
                <CommunityResponse>builder()
                .status("SUCCESS")
                .results(byUser)
                .build();

        log.info("CommunityController::findCommunityByUser response {}", byUser);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping( "/community/communities/{communityId}/user/{userId}")
    public List<UserDto> getRequestingUsersForCommunity
            (@PathVariable Long communityId, @PathVariable Long userId) throws Exception {
        return communityService.allRequestsForCommunity(communityId, userId);
    }

    @GetMapping( "/community/communities/user-requested-communities/{userId}")
    public ResponseEntity<APIResponse>  getRequestingCommunitiesForUser
            (@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
             @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
             @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
             @PathVariable Long userId) throws Exception {

        CommunityResponse communityResponse = communityService.
                allRequestedCommunitiesForUser(pageNumber, pageSize, keyword, userId);



        APIResponse<CommunityResponse> apiResponse = APIResponse.
                <CommunityResponse>builder()
                .status("SUCCESS")
                .results(communityResponse)
                .build();

        log.info("CommunityController::getRequestingCommunitiesForUser response {}", communityResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping( "/community/request/{communityId}/user/{userId}")
    public CommunityDto userRequestingCommunityApi(@PathVariable Long userId,
                                                @PathVariable Long communityId) throws Exception {
        return communityService.userRequestingCommunity(userId, communityId);
    }

    @PostMapping( "/community/request/{communityId}/user/{userId}/admin/{adminId}")
    public CommunityDto adminAcceptingRequest(@PathVariable Long adminId, @PathVariable Long userId,
                                                   @PathVariable Long communityId) throws Exception {
        return communityService.adminAcceptingUserRequest(adminId, userId, communityId);
    }
}
