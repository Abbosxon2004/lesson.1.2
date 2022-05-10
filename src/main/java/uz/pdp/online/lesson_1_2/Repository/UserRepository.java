package uz.pdp.online.lesson_1_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.lesson_1_2.Entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Integer id);
}
