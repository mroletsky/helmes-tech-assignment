package com.example.helmestechassignment.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "user_sector_selection")
@IdClass(UserSectorSelectionId.class)
public class UserSectorSelection {
    @Id @Column(name = "user_id") private Long userId;
    @Id @Column(name = "sector_id") private Long sectorId;
    @Column(name = "selected_at") private OffsetDateTime selectedAt;

    @PrePersist void prePersist() { if (selectedAt == null) selectedAt = OffsetDateTime.now(); }
}

