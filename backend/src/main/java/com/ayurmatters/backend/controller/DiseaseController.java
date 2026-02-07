package com.ayurmatters.backend.controller;

import com.ayurmatters.backend.dto.DiseaseRequestDTO;
import com.ayurmatters.backend.dto.DiseaseResponseDTO;
import com.ayurmatters.backend.entity.Disease;
import com.ayurmatters.backend.service.DiseaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diseases")
@CrossOrigin(origins = "*") // safety fallback
public class DiseaseController {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @PostMapping
    public ResponseEntity<DiseaseResponseDTO> createDisease(
            @RequestBody DiseaseRequestDTO request) {

        Disease disease = diseaseService.saveOrUpdateDisease(
                request.getDiseaseName(),
                request.getSymptoms(),
                request.getMedicines(),
                request.getDescription(),
                request.getAyurvedicNotes(),
                request.getGeneralNotes()
        );

        return ResponseEntity.ok(toDTO(disease));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiseaseResponseDTO>> search(
            @RequestParam String type,
            @RequestParam String q) {

        List<Disease> diseases = diseaseService.search(type, q);

        return ResponseEntity.ok(
                diseases.stream().map(this::toDTO).collect(Collectors.toList())
        );
    }

    private DiseaseResponseDTO toDTO(Disease disease) {
        DiseaseResponseDTO dto = new DiseaseResponseDTO();
        dto.setId(disease.getId());
        dto.setDiseaseName(disease.getName());
        dto.setDescription(disease.getDescription());
        dto.setAyurvedicNotes(disease.getAyurvedicNotes());
        dto.setGeneralNotes(disease.getGeneralNotes());
        dto.setSymptoms(
                disease.getSymptoms()
                        .stream()
                        .map(s -> s.getName())
                        .collect(Collectors.toList())
        );
        dto.setMedicines(
                disease.getMedicines()
                        .stream()
                        .map(m -> m.getName())
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
