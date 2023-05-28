package com.rbaliwal00.todoappusingjsp.service;

import com.rbaliwal00.todoappusingjsp.dto.*;
import com.rbaliwal00.todoappusingjsp.exception.ResourceNotFoundException;
import com.rbaliwal00.todoappusingjsp.model.Community;
import com.rbaliwal00.todoappusingjsp.model.User;
import com.rbaliwal00.todoappusingjsp.repository.CommunityRepository;
import com.rbaliwal00.todoappusingjsp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommunityServiceImpl implements CommunityService {
    private final ModelMapper modelMapper;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    public CommunityServiceImpl(ModelMapper modelMapper, CommunityRepository communityRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }

//    public Community communityToCommunityDto(){
//        return null;
//    }

    public CommunityDto communityToCommunityDto(Community community){
        CommunityDto communityDto = new CommunityDto();
        communityDto.setId(community.getId());
        communityDto.setName(community.getName());
        communityDto.setDescription(community.getDescription());
        communityDto.setAdmin(modelMapper.map(community.getAdmin(), UserDto.class));

        List<User> requestingMembersList = community.getRequestingMembers();
        List<UserDto> requestingMembers = new ArrayList<>();
        for(User u: requestingMembersList){
            requestingMembers.add(modelMapper.map(u, UserDto.class));
        }
        communityDto.setRequestingUsers(requestingMembers);

        List<User> membersList = community.getMembers();
        List<UserDto> members = new ArrayList<>();
        for(User u: membersList){
            members.add(modelMapper.map(u, UserDto.class));
        }
        communityDto.setMembers(members);
        return communityDto;
    }

    @Override
    public CommunityDto createCommunity(User user, CommunityDto communityDto) throws Exception {
        Community community = new Community();
        community.setName(communityDto.getName());
        community.setDescription(communityDto.getDescription());
        community.setAdmin(user);
        Community save = communityRepository.save(community);
        return communityToCommunityDto(save);
    }

    @Override
    public CommunityResponse findAll(Integer pageNumber, Integer pageSize, String keyword) {
        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Community> pageCommunity = communityRepository.findByNameContaining(keyword,p);

        return getCommunityResponse(pageCommunity);
    }

    @Override
    public CommunityDto userRequestingCommunity(Long userId, Long communityId) throws Exception {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new ResourceNotFoundException("Community", " Id ", communityId));

        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        if(community.getMembers().contains(user) || community.getRequestingMembers().contains(user)){
            return null;
        }

        community.getRequestingMembers().add(user);
        user.getRequestedCommunities().add(community);
        Community save = communityRepository.save(community);

        return communityToCommunityDto(save);
    }

    @Override
    public CommunityDto adminAcceptingUserRequest(Long adminId, Long userId, Long communityId) throws Exception {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new ResourceNotFoundException("Community", " Id ", communityId));

        if(community.getAdmin().getId() != adminId){
            throw new Exception("Not Authorized");
        }

        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        if(!community.getRequestingMembers().contains(user)){
            throw new Exception("Bad Request: This user has not requested to join this community");
        }

        if(community.getMembers().contains(user)){
            throw new Exception("Bad Request: User is already in the community");
        }

        community.getRequestingMembers().remove(user);
        user.getRequestedCommunities().remove(community);

        community.getMembers().add(user);
        user.getCommunities().add(community);

        Community save = communityRepository.save(community);

        return communityToCommunityDto(save);
    }

    @Override
    public CommunityResponse allRequestedCommunitiesForUser(Integer pageNumber, Integer pageSize, String keyword,Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Community> pageCommunity = communityRepository.findCommunityByRequestingMembersAndNameContaining(user,keyword,p);

        return getCommunityResponse(pageCommunity);
    }

    @Override
    public CommunityDto findById(Long communityId) {
        Community community = communityRepository.findById(communityId).
                orElseThrow(() -> new ResourceNotFoundException("Community", " Id ", communityId));
        return communityToCommunityDto(community);
    }

    @Override
    public List<UserDto> allRequestsForCommunity(Long communityId, Long userId) throws Exception {
        Community community = communityRepository.
                findById(communityId).
                orElseThrow(() -> new ResourceNotFoundException("Community", " Id ", communityId));

        if(community.getAdmin().getId() != userId){
            throw new Exception("Not Authorized");
        }
        List<UserDto> userDtos = new ArrayList<>();
        for(User u: community.getRequestingMembers()){
            userDtos.add(modelMapper.map(u, UserDto.class));
        }

        return userDtos;
    }

    @Override
    public CommunityResponse findByUser(Integer pageNumber, Integer pageSize, String keyword,Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Community> pageCommunity = communityRepository.findCommunityByAdminAndNameContaining(user,keyword,p);

        return getCommunityResponse(pageCommunity);
    }

    private CommunityResponse getCommunityResponse(Page<Community> pageCommunity) {
        List<Community> content = pageCommunity.getContent();
        List<CommunityDto> list = new ArrayList<>();
        for(Community i: content){
            list.add(modelMapper.map(i, CommunityDto.class));
        }

        CommunityResponse communityResponse = new CommunityResponse();
        communityResponse.setContent(list);
        communityResponse.setPageNumber(pageCommunity.getNumber());
        communityResponse.setPageSize(pageCommunity.getSize());
        communityResponse.setTotalElements(pageCommunity.getTotalElements());
        communityResponse.setTotalPages(pageCommunity.getTotalPages());
        communityResponse.setLastPage(pageCommunity.isLast());
        return communityResponse;
    }


}
