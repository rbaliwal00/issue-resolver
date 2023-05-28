package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.*;
import com.rbaliwal00.todoappusingjsp.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommunityService {
    CommunityDto createCommunity(User user, CommunityDto communityDto) throws Exception;
    CommunityResponse findAll(Integer pageNumber, Integer pageSize, String keyword);
    CommunityDto userRequestingCommunity(Long userId, Long communityId) throws Exception;
    CommunityDto findById(Long communityId);
    List<UserDto> allRequestsForCommunity(Long communityId, Long userId) throws Exception;

    @Cacheable(value = "user_communities")
    CommunityResponse findByUser(Integer pageNumber, Integer pageSize, String keyword,Long userId);

    CommunityDto adminAcceptingUserRequest(Long adminId, Long userId, Long communityId) throws Exception;

    CommunityResponse allRequestedCommunitiesForUser(Integer pageNumber,
                                                     Integer pageSize,
                                                     String keyword,
                                                     Long userId);
}
