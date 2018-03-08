package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**  
 * @author PC
 */
public interface UserBeanJpaRepository extends JpaRepository<UserBean, Long> {

    /**
     *  Jpa Query which returns a ShiftItem whose {@link Long} id matches the id parameter
     * @param name  {@link String} 
     * @return  Returns the {@link UserBean} with 
     */
    @Query("SELECT u FROM UserBean u WHERE u.name = :name")
    public UserBean findUserByName(@Param("name") String name);
    

}
