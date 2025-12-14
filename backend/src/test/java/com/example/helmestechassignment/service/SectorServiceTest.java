package com.example.helmestechassignment.service;

import com.example.helmestechassignment.dto.SectorTreeDto;
import com.example.helmestechassignment.mapper.SectorMapper;
import com.example.helmestechassignment.model.Sector;
import com.example.helmestechassignment.repo.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SectorServiceTest {

    private SectorRepository sectorRepository;
    private SectorService sectorService;

    @BeforeEach
    void setUp() {
        sectorRepository = mock(SectorRepository.class);
        SectorMapper sectorMapper = new SectorMapper();
        sectorService = new SectorService(sectorRepository, sectorMapper);
    }

    @Test
    void getSectorTree_buildsCorrectTree() {
        // Create mock sectors
        List<Sector> mockSectors = getSectors();

        when(sectorRepository.findAll(Sort.by("path"))).thenReturn(mockSectors);

        // Execute
        List<SectorTreeDto> tree = sectorService.getSectorTree();

        // Assertions
        assertEquals(1, tree.size());
        SectorTreeDto rootDto = tree.getFirst();
        assertEquals("Manufacturing", rootDto.getName());
        assertEquals(1, rootDto.getChildren().size());

        SectorTreeDto childDto = rootDto.getChildren().getFirst();
        assertEquals("Food and Beverage", childDto.getName());
        assertEquals(1, childDto.getChildren().size());

        SectorTreeDto grandChildDto = childDto.getChildren().getFirst();
        assertEquals("Bakery", grandChildDto.getName());
        assertTrue(grandChildDto.getChildren().isEmpty());
    }

    private static List<Sector> getSectors() {
        Sector root = new Sector();
        root.setId(1L);
        root.setName("Manufacturing");
        root.setPath("manufacturing");

        Sector child = new Sector();
        child.setId(2L);
        child.setName("Food and Beverage");
        child.setPath("manufacturing.food_and_beverage");

        Sector grandChild = new Sector();
        grandChild.setId(3L);
        grandChild.setName("Bakery");
        grandChild.setPath("manufacturing.food_and_beverage.bakery");

        return List.of(root, child, grandChild);
    }


}

