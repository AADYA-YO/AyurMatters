@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    List<Disease> findByNameContainingIgnoreCase(String name);

    @Query("""
        SELECT DISTINCT d FROM Disease d
        JOIN d.symptoms s
        WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Disease> findBySymptomName(@Param("q") String q);

    @Query("""
        SELECT DISTINCT d FROM Disease d
        JOIN d.medicines m
        WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Disease> findByMedicineName(@Param("q") String q);
}
