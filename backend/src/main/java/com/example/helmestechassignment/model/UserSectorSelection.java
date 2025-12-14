package com.example.helmestechassignment.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "user_sector_selection")
@IdClass(UserSectorSelectionId.class)
public class UserSectorSelection {
    @Id @Column(name = "user_id") private Integer userId;
    @Id @Column(name = "sector_id") private Integer sectorId;
    @Column(name = "selected_at") private OffsetDateTime selectedAt;

    @PrePersist void prePersist() { if (selectedAt == null) selectedAt = OffsetDateTime.now(); }
}

