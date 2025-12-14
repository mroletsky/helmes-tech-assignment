package com.example.helmestechassignment.model;

import java.io.Serializable;
import java.util.Objects;

public class UserSectorSelectionId implements Serializable {
    private Long userId;
    private Long sectorId;

    public UserSectorSelectionId() {}

    public UserSectorSelectionId(Long userId, Long sectorId) {
        this.userId = userId;
        this.sectorId = sectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSectorSelectionId that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(sectorId, that.sectorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sectorId);
    }
}
