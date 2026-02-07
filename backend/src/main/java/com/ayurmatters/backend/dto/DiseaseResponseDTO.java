package com.ayurmatters.backend.dto;

import java.util.Set;

public class DiseaseResponseDTO {

    private Long id;
    private String diseaseName;
    private Set<String> symptoms;
    private Set<String> medicines;
    private String description;
    private String ayurvedicNotes;
    private String generalNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Set<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<String> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<String> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<String> medicines) {
        this.medicines = medicines;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAyurvedicNotes() {
        return ayurvedicNotes;
    }

    public void setAyurvedicNotes(String ayurvedicNotes) {
        this.ayurvedicNotes = ayurvedicNotes;
    }

    public String getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(String generalNotes) {
        this.generalNotes = generalNotes;
    }
}
