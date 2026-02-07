package com.ayurmatters.backend.repository;

import com.ayurmatters.backend.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    @Query("""
        SELECT DISTINCT d FROM Disease d
        WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Disease> searchByDiseaseName(@Param("q") String q);

    @Query("""
        SELECT DISTINCT d FROM Disease d
        LEFT JOIN d.symptoms s
        WHERE s IS NOT NULL
        AND LOWER(s.name) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Disease> searchBySymptomName(@Param("q") String q);

    @Query("""
        SELECT DISTINCT d FROM Disease d
        LEFT JOIN d.medicines m
        WHERE m IS NOT NULL
        AND LOWER(m.name) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Disease> searchByMedicineName(@Param("q") String q);
}
