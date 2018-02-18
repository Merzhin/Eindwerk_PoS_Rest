package be.livingsmart.eindwerk;


import be.livingsmart.eindwerk.domain.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemJpaRepository extends JpaRepository<OrderedItem, Long> {

}
