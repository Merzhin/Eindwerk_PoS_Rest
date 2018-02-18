package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<Stock, Long> {

}