package uz.pdp.online.lesson_1_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.lesson_1_2.Entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    boolean existsByName(String name);
}
