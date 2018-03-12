package be.livingsmart.eindwerk;

import be.livingsmart.hdr.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *  Jpa specific extension of Repository, using {@link OrderedItem}s and {@link Long} id's 
 * @author Pieter
 */
public interface OrderedItemJpaRepository extends JpaRepository<OrderedItem, Long> {

}
