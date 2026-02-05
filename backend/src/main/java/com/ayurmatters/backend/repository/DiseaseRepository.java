package com.ayurmatters.backend.repository;

import com.ayurmatters.backend.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    Optional<Disease> findByNameIgnoreCase(String name);

    List<Disease> findByNameContainingIgnoreCase(String name);

    List<Disease> findBySymptomsNameIgnoreCase(String symptomName);

    List<Disease> findByMedicinesNameIgnoreCase(String medicineName);
}

