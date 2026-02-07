package com.ayurmatters.backend.dto;

import java.util.List;

public class DiseaseResponseDTO {

    private Long id;
    private String diseaseName;
    private List<String> symptoms;
    private List<String> medicines;
    private String description;
    private String ayurvedicNotes;
    private String generalNotes;

    public DiseaseResponseDTO() {}

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

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public List<String> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<String> medicines) {
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
