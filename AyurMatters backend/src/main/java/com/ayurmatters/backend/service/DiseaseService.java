package com.ayurmatters.backend.service;

import com.ayurmatters.backend.dto.DiseaseResponseDTO;
import com.ayurmatters.backend.entity.Disease;
import com.ayurmatters.backend.entity.Medicine;
import com.ayurmatters.backend.entity.Symptom;
import com.ayurmatters.backend.repository.DiseaseRepository;
import com.ayurmatters.backend.repository.MedicineRepository;
import com.ayurmatters.backend.repository.SymptomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Collectors;


@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final SymptomRepository symptomRepository;
    private final MedicineRepository medicineRepository;

    public DiseaseService(DiseaseRepository diseaseRepository,
                          SymptomRepository symptomRepository,
                          MedicineRepository medicineRepository) {
        this.diseaseRepository = diseaseRepository;
        this.symptomRepository = symptomRepository;
        this.medicineRepository = medicineRepository;
    }

    /* =========================
       GET BY ID (DTO RETURN)
       ========================= */

    @Transactional(readOnly = true)
    public Optional<DiseaseResponseDTO> findById(Long id) {
        return diseaseRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    /* =========================
       SEARCH METHODS (DTO RETURN)
       ========================= */

    @Transactional(readOnly = true)
    public List<DiseaseResponseDTO> searchByDiseaseName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return diseaseRepository.findByNameContainingIgnoreCase(name.trim())
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DiseaseResponseDTO> searchBySymptomName(String symptomName) {
        if (symptomName == null || symptomName.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return diseaseRepository.findBySymptomsNameIgnoreCase(symptomName.trim())
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DiseaseResponseDTO> searchByMedicineName(String medicineName) {
        if (medicineName == null || medicineName.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return diseaseRepository.findByMedicinesNameIgnoreCase(medicineName.trim())
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /* =========================
       SAVE / UPDATE
       ========================= */

    @Transactional
    public DiseaseResponseDTO saveOrUpdateDisease(String diseaseName,
                                                  Set<String> symptomNames,
                                                  Map<String, String> medicineNameToUsageNotes,
                                                  String generalNotes) {

        if (diseaseName == null || diseaseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Disease name cannot be null or empty");
        }

        Disease disease = diseaseRepository.findByNameIgnoreCase(diseaseName.trim())
                .orElseGet(() -> {
                    Disease newDisease = new Disease();
                    newDisease.setName(diseaseName.trim());
                    return newDisease;
                });

        disease.setGeneralNotes(generalNotes);

        if (symptomNames != null) {
            for (String symptomName : symptomNames) {
                if (symptomName != null && !symptomName.trim().isEmpty()) {
                    Symptom symptom = findOrCreateSymptom(symptomName.trim());
                    disease.getSymptoms().add(symptom);
                }
            }
        }

        if (medicineNameToUsageNotes != null) {
            for (Map.Entry<String, String> entry : medicineNameToUsageNotes.entrySet()) {
                String medicineName = entry.getKey();
                String usageNotes = entry.getValue();

                if (medicineName != null && !medicineName.trim().isEmpty()) {
                    Medicine medicine = findOrCreateMedicine(medicineName.trim(), usageNotes);
                    disease.getMedicines().add(medicine);
                }
            }
        }

        Disease saved = diseaseRepository.save(disease);
        return convertToResponseDTO(saved);
    }

    /* =========================
       PRIVATE HELPERS
       ========================= */

    private Symptom findOrCreateSymptom(String name) {
        return symptomRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Symptom newSymptom = new Symptom();
                    newSymptom.setName(name);
                    return symptomRepository.save(newSymptom);
                });
    }

    private Medicine findOrCreateMedicine(String name, String usageNotes) {
        Medicine medicine = medicineRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Medicine newMedicine = new Medicine();
                    newMedicine.setName(name);
                    return newMedicine;
                });

        if (usageNotes != null && !usageNotes.trim().isEmpty()) {
            medicine.setUsageNotes(usageNotes);
        }

        return medicineRepository.save(medicine);
    }

    private DiseaseResponseDTO convertToResponseDTO(Disease disease) {

        DiseaseResponseDTO response = new DiseaseResponseDTO();
        response.setId(disease.getId());
        response.setDiseaseName(disease.getName());
        response.setGeneralNotes(disease.getGeneralNotes());

        response.setSymptoms(
                disease.getSymptoms().stream()
                        .map(Symptom::getName)
                        .collect(Collectors.toSet())
        );

        response.setMedicines(
                disease.getMedicines().stream()
                        .collect(Collectors.toMap(
                                Medicine::getName,
                                med -> med.getUsageNotes() != null ? med.getUsageNotes() : ""
                        ))
        );


        return response;
    }
}
