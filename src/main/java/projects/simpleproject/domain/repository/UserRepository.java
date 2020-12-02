package projects.simpleproject.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.simpleproject.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
}
