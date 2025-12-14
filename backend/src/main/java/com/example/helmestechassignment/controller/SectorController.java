package com.example.helmestechassignment.controller;

import com.example.helmestechassignment.dto.SectorTreeDto;
import com.example.helmestechassignment.service.SectorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping("/tree")
    public List<SectorTreeDto> getSectorTree() {
        return sectorService.getSectorTree();
    }
}

