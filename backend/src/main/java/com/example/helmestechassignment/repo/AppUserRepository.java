package com.example.helmestechassignment.repo;

import com.example.helmestechassignment.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);

    List<AppUser> findByAgreeToTerms(boolean agreeToTerms);

    boolean existsByUsername(String username);
}
