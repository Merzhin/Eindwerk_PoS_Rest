package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.UserBean;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserBeanJpaRepository extends JpaRepository<UserBean, Long> {

    @Query("SELECT u FROM UserBean u WHERE u.name = :name")
    public UserBean findUserByName(@Param("name") String name);

}
