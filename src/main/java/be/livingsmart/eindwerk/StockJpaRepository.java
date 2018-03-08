package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  This class is currently not in use
 * @author Pieter
 */
public interface StockJpaRepository extends JpaRepository<Stock, Long> {

}