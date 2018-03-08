package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.OrderBean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Jpa specific extension of Repository, using {@link OrderBean}s and {@link Long} id's 
 * @author Pieter
 */
public interface OrderBeanJpaRepository extends JpaRepository<OrderBean, Long> {

}
