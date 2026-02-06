package com.ayurmatters.backend.controller;

import com.ayurmatters.backend.dto.DiseaseRequestDTO;
import com.ayurmatters.backend.dto.DiseaseResponseDTO;
import com.ayurmatters.backend.entity.Disease;
import com.ayurmatters.backend.entity.Medicine;
import com.ayurmatters.backend.entity.Symptom;
import com.ayurmatters.backend.service.DiseaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diseases")
@CrossOrigin(origins = "*") // safe for demo & Netlify
public class DiseaseController {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @PostMapping
    public ResponseEntity<DiseaseResponseDTO> createOrUpdateDisease(
            @RequestBody DiseaseRequestDTO request) {

        Disease disease = diseaseService.saveOrUpdateDisease(
                request.getDiseaseName(),
                request.getSymptoms(),
                request.getMedicines(),
                request.getDescription(),
                request.getAyurvedicNotes(),
                request.getGeneralNotes()
        );

        return ResponseEntity.ok(convertToResponseDTO(disease));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseResponseDTO> getDiseaseById(@PathVariable Long id) {
        return diseaseService.findById(id)
                .map(d -> ResponseEntity.ok(convertToResponseDTO(d)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiseaseResponseDTO>> searchDiseases(
            @RequestParam String type,
            @RequestParam String q) {

        List<Disease> diseases;

        switch (type.toLowerCase()) {
            case "disease":
                diseases = diseaseService.searchByDiseaseName(q);
                break;
            case "symptom":
                diseases = diseaseService.searchBySymptomName(q);
                break;
            case "medicine":
                diseases = diseaseService.searchByMedicineName(q);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                diseases.stream()
                        .map(this::convertToResponseDTO)
                        .collect(Collectors.toList())
        );
    }

    // ✅ FULLY NULL-SAFE CONVERTER
    private DiseaseResponseDTO convertToResponseDTO(Disease disease) {

        DiseaseResponseDTO response = new DiseaseResponseDTO();

        response.setId(disease.getId());
        response.setDiseaseName(disease.getName());
        response.setDescription(disease.getDescription());
        response.setAyurvedicNotes(disease.getAyurvedicNotes());
        response.setGeneralNotes(disease.getGeneralNotes());

        // 🔒 NULL-SAFE symptoms
        Set<String> symptoms = (disease.getSymptoms() == null)
                ? Collections.emptySet()
                : disease.getSymptoms()
                .stream()
                .map(Symptom::getName)
                .collect(Collectors.toSet());

        response.setSymptoms(symptoms);

        // 🔒 NULL-SAFE medicines
        Set<String> medicines = (disease.getMedicines() == null)
                ? Collections.emptySet()
                : disease.getMedicines()
                .stream()
                .map(Medicine::getName)
                .collect(Collectors.toSet());

        response.setMedicines(medicines);

        return response;
    }
}
