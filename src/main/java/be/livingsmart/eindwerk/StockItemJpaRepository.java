package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemJpaRepository extends JpaRepository<StockItem, Long> {

}
