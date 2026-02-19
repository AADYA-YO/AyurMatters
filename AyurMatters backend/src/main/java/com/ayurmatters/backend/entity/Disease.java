package com.ayurmatters.backend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 150)
    private String name;

    @Column(length = 10000)   // ← IMPORTANT: no TEXT, no @Lob
    private String generalNotes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "disease_symptoms",
            joinColumns = @JoinColumn(name = "disease_id"),
            inverseJoinColumns = @JoinColumn(name = "symptom_id")
    )
    private Set<Symptom> symptoms = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "disease_medicines",
            joinColumns = @JoinColumn(name = "disease_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    private Set<Medicine> medicines = new HashSet<>();

    public Disease() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGeneralNotes() { return generalNotes; }
    public void setGeneralNotes(String generalNotes) { this.generalNotes = generalNotes; }

    public Set<Symptom> getSymptoms() { return symptoms; }
    public void setSymptoms(Set<Symptom> symptoms) { this.symptoms = symptoms; }

    public Set<Medicine> getMedicines() { return medicines; }
    public void setMedicines(Set<Medicine> medicines) { this.medicines = medicines; }
}
