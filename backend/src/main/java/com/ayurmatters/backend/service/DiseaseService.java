import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.ayurmatters.backend.entity.Disease;
import com.ayurmatters.backend.entity.Symptom;
import com.ayurmatters.backend.entity.Medicine;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final SymptomRepository symptomRepository;
    private final MedicineRepository medicineRepository;

    public DiseaseService(
            DiseaseRepository diseaseRepository,
            SymptomRepository symptomRepository,
            MedicineRepository medicineRepository
    ) {
        this.diseaseRepository = diseaseRepository;
        this.symptomRepository = symptomRepository;
        this.medicineRepository = medicineRepository;
    }

    public Disease save(DiseaseRequestDTO dto) {

        Disease disease = new Disease();
        disease.setName(dto.getDiseaseName());
        disease.setDescription(dto.getDescription());
        disease.setAyurvedicNotes(dto.getAyurvedicNotes());
        disease.setGeneralNotes(dto.getGeneralNotes());

        // ✅ Convert symptom names → Symptom entities
        Set<Symptom> symptomEntities = dto.getSymptoms().stream()
                .map(name ->
                        symptomRepository.findByNameIgnoreCase(name)
                                .orElseGet(() -> {
                                    Symptom s = new Symptom();
                                    s.setName(name);
                                    return s;
                                })
                )
                .collect(Collectors.toSet());

        disease.setSymptoms(symptomEntities);

        // ✅ Convert medicine names → Medicine entities
        Set<Medicine> medicineEntities = dto.getMedicines().entrySet().stream()
                .map(entry ->
                        medicineRepository.findByNameIgnoreCase(entry.getKey())
                                .orElseGet(() -> {
                                    Medicine m = new Medicine();
                                    m.setName(entry.getKey());
                                    m.setUsageNotes(entry.getValue());
                                    return m;
                                })
                )
                .collect(Collectors.toSet());

        disease.setMedicines(medicineEntities);

        return diseaseRepository.save(disease);
    }

    public List<Disease> searchByDiseaseName(String q) {
        return diseaseRepository.findByNameContainingIgnoreCase(q);
    }

    public List<Disease> searchBySymptom(String q) {
        return diseaseRepository.findBySymptomName(q);
    }

    public List<Disease> searchByMedicine(String q) {
        return diseaseRepository.findByMedicineName(q);
    }

    public DiseaseResponseDTO toResponseDTO(Disease disease) {

        DiseaseResponseDTO dto = new DiseaseResponseDTO();
        dto.setId(disease.getId());
        dto.setDiseaseName(disease.getName());
        dto.setDescription(disease.getDescription());
        dto.setAyurvedicNotes(disease.getAyurvedicNotes());
        dto.setGeneralNotes(disease.getGeneralNotes());

        dto.setSymptoms(
                disease.getSymptoms()
                        .stream()
                        .map(Symptom::getName)
                        .collect(Collectors.toSet())
        );

        dto.setMedicines(
                disease.getMedicines()
                        .stream()
                        .map(Medicine::getName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
