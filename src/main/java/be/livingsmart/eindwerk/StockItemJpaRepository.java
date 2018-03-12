package be.livingsmart.eindwerk;

import be.livingsmart.hdr.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;


/** 
 *  Jpa specific extension of Repository, using {@link StockItem}s and {@link Long} id's 
 * @author PC
 */
public interface StockItemJpaRepository extends JpaRepository<StockItem, Long> {

}
