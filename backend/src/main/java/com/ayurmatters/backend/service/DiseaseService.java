package com.ayurmatters.backend.service;

import com.ayurmatters.backend.entity.Disease;
import com.ayurmatters.backend.repository.DiseaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    public Disease saveOrUpdateDisease(
            String name,
            List<String> symptoms,
            List<String> medicines,
            String description,
            String ayurvedicNotes,
            String generalNotes) {

        Disease disease = new Disease();
        disease.setName(name);
        disease.setDescription(description);
        disease.setAyurvedicNotes(ayurvedicNotes);
        disease.setGeneralNotes(generalNotes);

        disease.setSymptomsFromNames(symptoms);
        disease.setMedicinesFromNames(medicines);

        return diseaseRepository.save(disease);
    }

    public List<Disease> search(String type, String q) {
        return switch (type.toLowerCase()) {
            case "disease" -> diseaseRepository.findByNameContainingIgnoreCase(q);
            case "symptom" -> diseaseRepository.findBySymptomName(q);
            case "medicine" -> diseaseRepository.findByMedicineName(q);
            default -> List.of();
        };
    }
}
