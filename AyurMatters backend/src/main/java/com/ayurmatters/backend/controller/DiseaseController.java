package com.ayurmatters.backend.controller;

import com.ayurmatters.backend.dto.DiseaseRequestDTO;
import com.ayurmatters.backend.dto.DiseaseResponseDTO;
import com.ayurmatters.backend.service.DiseaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @PostMapping
    public ResponseEntity<DiseaseResponseDTO> createOrUpdateDisease(
            @RequestBody DiseaseRequestDTO request) {

        DiseaseResponseDTO response = diseaseService.saveOrUpdateDisease(
                request.getDiseaseName(),
                request.getSymptoms(),
                request.getMedicines(),
                request.getGeneralNotes()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseResponseDTO> getDiseaseById(@PathVariable Long id) {
        return diseaseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiseaseResponseDTO>> searchDiseases(
            @RequestParam String type,
            @RequestParam String q) {

        switch (type.toLowerCase()) {
            case "disease":
                return ResponseEntity.ok(diseaseService.searchByDiseaseName(q));
            case "symptom":
                return ResponseEntity.ok(diseaseService.searchBySymptomName(q));
            case "medicine":
                return ResponseEntity.ok(diseaseService.searchByMedicineName(q));
            default:
                return ResponseEntity.badRequest().build();
        }
    }
}
