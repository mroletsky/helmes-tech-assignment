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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserSectorServiceTest {

    private AppUserRepository userRepository;
    private UserSectorSelectionRepository selectionRepository;
    private SectorRepository sectorRepository;

    private UserSectorService userSectorService;

    @BeforeEach
    void setUp() {
        userRepository = mock(AppUserRepository.class);
        selectionRepository = mock(UserSectorSelectionRepository.class);
        sectorRepository = mock(SectorRepository.class);

        userSectorService = new UserSectorService(userRepository, selectionRepository, sectorRepository);
    }

    @Test
    void saveUserSectors_validRequest_savesSuccessfully() {

        // Given
        UserSectorSaveRequest request = new UserSectorSaveRequest();
        request.setUsername("TestUser");
        request.setAgreeToTerms(true);
        request.setSectorIds(List.of(1L, 2L));

        // Mock sector repository to return all requested IDs
        when(sectorRepository.findAllById(request.getSectorIds()))
                .thenReturn(List.of(
                        new Sector() {{ setId(1L); setName("Manufacturing"); }},
                        new Sector() {{ setId(2L); setName("Food"); }}
                ));

        // Mock user repo: simulate ID generation
        when(userRepository.findByUsername("TestUser")).thenReturn(Optional.empty());
        when(userRepository.save(any(AppUser.class))).thenAnswer(invocation -> {
            AppUser savedUser = invocation.getArgument(0);
            if (savedUser.getId() == null) {
                savedUser.setId(1L); // simulate DB auto-generated ID
            }
            return savedUser;
        });

        // When
        UserSectorResponse response = userSectorService.saveUserSectors(request);

        // Then
        assertNotNull(response.getUserId()); // now passes
        assertEquals("TestUser", response.getUsername());
        assertTrue(response.isAgreeToTerms());
        assertEquals(List.of(1L, 2L), response.getSectorIds());

        verify(selectionRepository).deleteByUserId(anyLong());
        verify(selectionRepository, times(2)).save(any(UserSectorSelection.class));
    }


    @Test
    void saveUserSectors_invalidSectorIds_throwsException() {

        UserSectorSaveRequest request = new UserSectorSaveRequest();
        request.setUsername("TestUser");
        request.setAgreeToTerms(true);
        request.setSectorIds(List.of(1L, 999L));

        // Only ID 1 exists
        when(sectorRepository.findAllById(request.getSectorIds()))
                .thenReturn(List.of(new Sector() {{ setId(1L); setName("Manufacturing"); }}));

        InvalidSectorException exception = assertThrows(
                InvalidSectorException.class,
                () -> userSectorService.saveUserSectors(request)
        );

        assertEquals("One or more selected sectors do not exist", exception.getMessage());

        // Ensure no save occurs
        verify(userRepository, never()).save(any());
        verify(selectionRepository, never()).save(any());
    }


}

