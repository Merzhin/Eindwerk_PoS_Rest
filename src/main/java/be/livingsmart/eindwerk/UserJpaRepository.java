package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User WHERE u.name = :name")
    public List<User> findUserByName(@Param("name") String name);

}
