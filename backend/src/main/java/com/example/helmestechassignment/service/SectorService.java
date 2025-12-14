package com.example.helmestechassignment.service;

import com.example.helmestechassignment.dto.SectorTreeDto;
import com.example.helmestechassignment.mapper.SectorMapper;
import com.example.helmestechassignment.model.Sector;
import com.example.helmestechassignment.repo.SectorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SectorService {

    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;

    public SectorService(SectorRepository sectorRepository, SectorMapper sectorMapper) {
        this.sectorRepository = sectorRepository;
        this.sectorMapper = sectorMapper;
    }

    /**
     * Fetches all sectors from the database and builds a tree-like structure in O(n) time complexity. Preserves order. Works with unlimited depth.
     * @return all sectors in a tree-like structure.
     */
    public List<SectorTreeDto> getSectorTree() {

        List<Sector> sectors = sectorRepository.findAll(Sort.by("path"));

        Map<String, SectorTreeDto> pathToNode = new LinkedHashMap<>();
        List<SectorTreeDto> roots = new ArrayList<>();

        // 1. Create all nodes
        for (Sector sector : sectors) {
            SectorTreeDto dto = sectorMapper.toTreeDto(sector);
            pathToNode.put(dto.getPath(), dto);
        }

        // 2. Build tree
        for (SectorTreeDto node : pathToNode.values()) {
            String path = node.getPath();

            if (!path.contains(".")) {
                // root node
                roots.add(node);
            } else {
                String parentPath = path.substring(0, path.lastIndexOf('.'));
                SectorTreeDto parent = pathToNode.get(parentPath);

                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }

        return roots;
    }
}

