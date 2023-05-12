package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.*;
import com.rbaliwal00.todoappusingjsp.model.User;
import org.springframework.http.ResponseEntity;

public interface CommunityService {
    CommunityDto createCommunity(User user, CommunityDto communityDto) throws Exception;
    CommunityResponse findAll(Integer pageNumber, Integer pageSize, String keyword);
    CommunityDto userRequestingCommunity(Long userId, Long communityId) throws Exception;
    CommunityDto findById(Long communityId);
}
