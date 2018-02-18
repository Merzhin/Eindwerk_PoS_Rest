package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
