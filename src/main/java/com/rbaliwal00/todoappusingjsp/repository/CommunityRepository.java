package com.rbaliwal00.todoappusingjsp.repository;

import com.rbaliwal00.todoappusingjsp.dto.CommunityDto;
import com.rbaliwal00.todoappusingjsp.model.Community;
import com.rbaliwal00.todoappusingjsp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findByNameContaining(String keyword, Pageable p);
    Page<Community> findCommunityByAdminAndNameContaining(User admin, String keyword, Pageable p);
    Page<Community> findCommunityByRequestingMembersAndNameContaining(User user, String keyword, Pageable p);
}
