package com.ayurmatters.backend.controller;

import com.ayurmatters.backend.dto.DiseaseRequestDTO;
import com.ayurmatters.backend.dto.DiseaseResponseDTO;
import com.ayurmatters.backend.entity.Disease;
import com.ayurmatters.backend.entity.Medicine;
import com.ayurmatters.backend.entity.Symptom;
import com.ayurmatters.backend.service.DiseaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @PostMapping
    public ResponseEntity<DiseaseResponseDTO> createOrUpdateDisease(@RequestBody DiseaseRequestDTO request) {
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
                .map(disease -> ResponseEntity.ok(convertToResponseDTO(disease)))
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

        List<DiseaseResponseDTO> response = diseases.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private DiseaseResponseDTO convertToResponseDTO(Disease disease) {
        DiseaseResponseDTO response = new DiseaseResponseDTO();
        response.setId(disease.getId());
        response.setDiseaseName(disease.getName());
        response.setDescription(disease.getDescription());
        response.setAyurvedicNotes(disease.getAyurvedicNotes());
        response.setGeneralNotes(disease.getGeneralNotes());
        response.setSymptoms(
                disease.getSymptoms().stream()
                        .map(Symptom::getName)
                        .collect(Collectors.toSet())
        );
        response.setMedicines(
                disease.getMedicines().stream()
                        .map(Medicine::getName)
                        .collect(Collectors.toSet())
        );
        return response;
    }
}
