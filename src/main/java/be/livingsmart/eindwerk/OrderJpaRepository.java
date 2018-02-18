package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

}
