package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *  Jpa specific extension of Repository, using {@link Item}s and {@link Long} id's 
 * @author Pieter
 */
public interface ItemJpaRepository extends JpaRepository<Item, Long> {
    
    /**
     *  Returns all {@link Item}s where isFavorite = the given {@link Boolean}
     * @param bool {@link Boolean}
     * @return  {@link List} of {@link Item}s
     */
    @Query("SELECT i FROM Item i WHERE i.isFavorite = :bool")
    public List<Item> getAllFavorites(@Param("bool") boolean bool);
    
}
