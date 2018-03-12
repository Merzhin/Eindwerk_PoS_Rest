package be.livingsmart.eindwerk;

import be.livingsmart.hdr.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  This class is currently not in use
 * @author Pieter
 */
public interface StockJpaRepository extends JpaRepository<Stock, Long> {

}