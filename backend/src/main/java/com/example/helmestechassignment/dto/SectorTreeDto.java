package com.example.helmestechassignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * A tree-friendly dto
 */
@Getter
@Setter
public class SectorTreeDto {

    private Long id;
    private String name;
    private String path;
    private List<SectorTreeDto> children = new ArrayList<>();

    public SectorTreeDto(Long id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }
}

