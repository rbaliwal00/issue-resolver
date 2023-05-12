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
//        List <UserDto> userDtos = new ArrayList<>();
//        for(User i: community.getMembers()){
//            userDtos.add(modelMapper.map(i, UserDto.class));
//        }
//
//        List <UserDto> userDtosForRequestingMembers = new ArrayList<>();
//        for(User i: community.getRequestingMembers()){
//            userDtosForRequestingMembers.add(modelMapper.map(i, UserDto.class));
//        }

//        List<User> votesList = issue.getVoters();
//        List<UserDto> votes = new ArrayList<>();
//        for(User u : votesList){
//            votes.add(modelMapper.map(u, UserDto.class));
//        }
//        dto.setVotes(votes);

        List<User> requestingMembersList = community.getRequestingMembers();
        List<UserDto> requestingMembers = new ArrayList<>();
        for(User u: requestingMembersList){
            requestingMembers.add(modelMapper.map(u, UserDto.class));
        }
        communityDto.setRequestingUsers(requestingMembers);
        System.out.println(requestingMembers);
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

    @Override
    public CommunityDto userRequestingCommunity(Long userId, Long communityId) throws Exception {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new ResourceNotFoundException("Community", " Id ", communityId));

        System.out.println(communityToCommunityDto(community));
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
    public CommunityDto findById(Long communityId) {
        Community community = communityRepository.findById(communityId).
                orElseThrow(() -> new ResourceNotFoundException("Community", " Id ", communityId));

        return communityToCommunityDto(community);
    }


}
