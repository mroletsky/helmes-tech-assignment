package com.example.helmestechassignment.mapper;

import com.example.helmestechassignment.dto.SectorTreeDto;
import com.example.helmestechassignment.model.Sector;
import org.springframework.stereotype.Component;

@Component
public class SectorMapper {

    public SectorTreeDto toTreeDto(Sector sector) {
        return new SectorTreeDto(
                sector.getId(),
                sector.getName(),
                sector.getPath()
        );
    }
}
