package com.example.helmestechassignment.repo;

import com.example.helmestechassignment.model.Sector;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Integer> {
    Optional<Sector> findByPath(String path);

    @Query(value = "SELECT * FROM sector WHERE path <@ CAST(:path AS ltree) ORDER BY path", nativeQuery = true)
    List<Sector> findDescendants(@Param("path") String path);

    @Query(value = "SELECT * FROM sector WHERE path ~ (CAST(:parent AS text) || '.*{1}')::lquery ORDER BY path", nativeQuery = true)
    List<Sector> findImmediateChildren(@Param("parent") String parent);
}
