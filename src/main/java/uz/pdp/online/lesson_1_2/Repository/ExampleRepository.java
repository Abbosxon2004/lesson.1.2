package uz.pdp.online.lesson_1_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.lesson_1_2.Entity.Example;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Integer> {
}
