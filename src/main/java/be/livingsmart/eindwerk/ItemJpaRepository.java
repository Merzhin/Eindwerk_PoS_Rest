package be.livingsmart.eindwerk;

import domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemJpaRepository extends JpaRepository<Item, Long> {

}
