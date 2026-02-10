import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @PostMapping
    public ResponseEntity<DiseaseResponseDTO> create(@RequestBody DiseaseRequestDTO request) {
        Disease disease = diseaseService.save(request);
        return ResponseEntity.ok(diseaseService.toResponseDTO(disease));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiseaseResponseDTO>> search(
            @RequestParam String type,
            @RequestParam String q) {

        List<Disease> diseases = switch (type.toLowerCase()) {
            case "disease" -> diseaseService.searchByDiseaseName(q);
            case "symptom" -> diseaseService.searchBySymptom(q);
            case "medicine" -> diseaseService.searchByMedicine(q);
            default -> List.of();
        };

        return ResponseEntity.ok(
                diseases.stream()
                        .map(diseaseService::toResponseDTO)
                        .toList()
        );
    }
}
