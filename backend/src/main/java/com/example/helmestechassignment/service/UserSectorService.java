package com.example.helmestechassignment.service;

import com.example.helmestechassignment.dto.UserSectorResponse;
import com.example.helmestechassignment.dto.UserSectorSaveRequest;
import com.example.helmestechassignment.exception.InvalidSectorException;
import com.example.helmestechassignment.model.AppUser;
import com.example.helmestechassignment.model.Sector;
import com.example.helmestechassignment.model.UserSectorSelection;
import com.example.helmestechassignment.repo.AppUserRepository;
import com.example.helmestechassignment.repo.SectorRepository;
import com.example.helmestechassignment.repo.UserSectorSelectionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSectorService {

    private final AppUserRepository userRepository;
    private final UserSectorSelectionRepository selectionRepository;
    private final SectorRepository sectorRepository;

    public UserSectorService(
            AppUserRepository userRepository,
            UserSectorSelectionRepository selectionRepository,
            SectorRepository sectorRepository
    ) {
        this.userRepository = userRepository;
        this.selectionRepository = selectionRepository;
        this.sectorRepository = sectorRepository;
    }

    @Transactional
    public UserSectorResponse saveUserSectors(@Valid UserSectorSaveRequest request) {

        // 1. Validate sector IDs exist
        List<Long> requestedIds = request.getSectorIds();

        List<Long> existingIds = sectorRepository.findAllById(requestedIds)
                .stream()
                .map(Sector::getId)
                .toList();

        if (existingIds.size() != requestedIds.size()) {
            throw new InvalidSectorException("One or more selected sectors do not exist");
        }

        // 2. Create or update user
        AppUser user = userRepository
                .findByUsername(request.getUsername())
                .orElseGet(AppUser::new);

        user.setUsername(request.getUsername());
        user.setAgreeToTerms(request.isAgreeToTerms());
        user = userRepository.save(user);

        // 3. Replace sector selections (edit-safe)
        selectionRepository.deleteByUserId(user.getId());

        for (Long sectorId : request.getSectorIds()) {
            UserSectorSelection selection = new UserSectorSelection();
            selection.setUserId(user.getId());
            selection.setSectorId(sectorId);
            selectionRepository.save(selection);
        }

        // 4. Return data for form refill
        return new UserSectorResponse(
                user.getId(),
                user.getUsername(),
                user.isAgreeToTerms(),
                request.getSectorIds()
        );
    }

    public UserSectorResponse getUserData(String username) {

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Long> sectorIds = selectionRepository.findByUserId(user.getId())
                .stream()
                .map(UserSectorSelection::getSectorId)
                .toList();

        return new UserSectorResponse(
                user.getId(),
                user.getUsername(),
                user.isAgreeToTerms(),
                sectorIds
        );
    }
}

