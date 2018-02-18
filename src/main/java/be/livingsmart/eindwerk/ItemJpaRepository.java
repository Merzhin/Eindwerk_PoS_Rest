package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemJpaRepository extends JpaRepository<Item, Long> {

}
