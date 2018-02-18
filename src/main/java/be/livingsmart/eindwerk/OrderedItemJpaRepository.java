package be.livingsmart.eindwerk;

import domain.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemJpaRepository extends JpaRepository<OrderedItem, Long> {

}
