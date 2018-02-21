package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.OrderBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBeanJpaRepository extends JpaRepository<OrderBean, Long> {

}
