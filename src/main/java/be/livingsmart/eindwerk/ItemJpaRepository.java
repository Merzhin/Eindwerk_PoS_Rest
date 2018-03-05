package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ItemJpaRepository extends JpaRepository<Item, Long> {
    
    @Query("SELECT i FROM Item i WHERE i.isFavorite = :bool")
    public List<Item> getAllFavorites(@Param("bool") boolean bool);
    
}
