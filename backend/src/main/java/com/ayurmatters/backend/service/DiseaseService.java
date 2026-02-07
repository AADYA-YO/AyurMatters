package com.ayurmatters.backend.service;

import com.ayurmatters.backend.entity.Disease;
import com.ayurmatters.backend.repository.DiseaseRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    public Disease saveOrUpdateDisease(
            String diseaseName,
            List<String> symptoms,
            Object medicines,
            String description,
            String ayurvedicNotes,
            String generalNotes) {

        return diseaseRepository.save(
                Disease.builder()
                        .name(diseaseName)
                        .description(description)
                        .ayurvedicNotes(ayurvedicNotes)
                        .generalNotes(generalNotes)
                        .build()
        );
    }

    public List<Disease> searchByDiseaseName(String q) {
        try {
            return diseaseRepository.searchByDiseaseName(q);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Disease> searchBySymptomName(String q) {
        try {
            return diseaseRepository.searchBySymptomName(q);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Disease> searchByMedicineName(String q) {
        try {
            return diseaseRepository.searchByMedicineName(q);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public java.util.Optional<Disease> findById(Long id) {
        return diseaseRepository.findById(id);
    }
}
