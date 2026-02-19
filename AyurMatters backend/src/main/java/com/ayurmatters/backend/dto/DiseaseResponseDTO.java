package com.ayurmatters.backend.dto;

import java.util.Map;
import java.util.Set;

public class DiseaseResponseDTO {

    private Long id;
    private String diseaseName;
    private Set<String> symptoms;
    private Map<String, String> medicines;   // ← changed
    private String generalNotes;

    public DiseaseResponseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDiseaseName() { return diseaseName; }
    public void setDiseaseName(String diseaseName) { this.diseaseName = diseaseName; }

    public Set<String> getSymptoms() { return symptoms; }
    public void setSymptoms(Set<String> symptoms) { this.symptoms = symptoms; }

    public Map<String, String> getMedicines() { return medicines; }
    public void setMedicines(Map<String, String> medicines) { this.medicines = medicines; }

    public String getGeneralNotes() { return generalNotes; }
    public void setGeneralNotes(String generalNotes) { this.generalNotes = generalNotes; }
}
