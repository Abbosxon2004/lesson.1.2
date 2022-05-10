package uz.pdp.online.lesson_1_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.lesson_1_2.Entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
