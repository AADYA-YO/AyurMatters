package com.ayurmatters.backend.dto;

import java.util.Map;
import java.util.Set;

public class DiseaseRequestDTO {

    private String diseaseName;
    private String generalNotes;
    private Set<String> symptoms;
    private Map<String, String> medicines;

    public DiseaseRequestDTO() {
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }


    public String getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(String generalNotes) {
        this.generalNotes = generalNotes;
    }

    public Set<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<String> symptoms) {
        this.symptoms = symptoms;
    }

    public Map<String, String> getMedicines() {
        return medicines;
    }

    public void setMedicines(Map<String, String> medicines) {
        this.medicines = medicines;
    }
}
