package com.example.helmestechassignment.repo;

import com.example.helmestechassignment.model.UserSectorSelection;
import com.example.helmestechassignment.model.UserSectorSelectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSectorSelectionRepository extends JpaRepository<UserSectorSelection, UserSectorSelectionId> {
    List<UserSectorSelection> findByUserId(Integer userId);
    List<UserSectorSelection> findBySectorId(Integer sectorId);
}
