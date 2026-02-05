package com.ayurmatters.backend.service;

import com.ayurmatters.backend.entity.Disease;
import com.ayurmatters.backend.entity.Medicine;
import com.ayurmatters.backend.entity.Symptom;
import com.ayurmatters.backend.repository.DiseaseRepository;
import com.ayurmatters.backend.repository.MedicineRepository;
import com.ayurmatters.backend.repository.SymptomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    @Transactional(readOnly = true)
    public Optional<Disease> findById(Long id) {
        return diseaseRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Disease> searchByDiseaseName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return diseaseRepository.findByNameContainingIgnoreCase(name.trim());
    }

    @Transactional(readOnly = true)
    public List<Disease> searchBySymptomName(String symptomName) {
        if (symptomName == null || symptomName.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return diseaseRepository.findBySymptomsNameIgnoreCase(symptomName.trim());
    }

    @Transactional(readOnly = true)
    public List<Disease> searchByMedicineName(String medicineName) {
        if (medicineName == null || medicineName.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return diseaseRepository.findByMedicinesNameIgnoreCase(medicineName.trim());
    }

    @Transactional
    public Disease saveOrUpdateDisease(String diseaseName,
                                       Set<String> symptomNames,
                                       Map<String, String> medicineNameToUsageNotes,
                                       String description,
                                       String ayurvedicNotes,
                                       String generalNotes) {

        if (diseaseName == null || diseaseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Disease name cannot be null or empty");
        }

        // Find existing disease or create new one
        Disease disease = diseaseRepository.findByNameIgnoreCase(diseaseName.trim())
                .orElseGet(() -> {
                    Disease newDisease = new Disease();
                    newDisease.setName(diseaseName.trim());
                    return newDisease;
                });

        // Update disease fields
        disease.setDescription(description);
        disease.setAyurvedicNotes(ayurvedicNotes);
        disease.setGeneralNotes(generalNotes);

        // Process symptoms - merge into existing set
        if (symptomNames != null) {
            for (String symptomName : symptomNames) {
                if (symptomName != null && !symptomName.trim().isEmpty()) {
                    Symptom symptom = findOrCreateSymptom(symptomName.trim());
                    disease.getSymptoms().add(symptom);
                }
            }
        }

        // Process medicines - merge into existing set
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

        return diseaseRepository.save(disease);
    }

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

        // Update usage notes if provided
        if (usageNotes != null && !usageNotes.trim().isEmpty()) {
            medicine.setUsageNotes(usageNotes);
        }

        return medicineRepository.save(medicine);
    }
}
