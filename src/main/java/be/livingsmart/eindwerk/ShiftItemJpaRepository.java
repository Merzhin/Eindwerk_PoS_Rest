package be.livingsmart.eindwerk;


import be.livingsmart.hdr.Shift;
import be.livingsmart.hdr.ShiftItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/** 
 *  Jpa specific extension of Repository, using {@link ShiftItem}s and {@link Long} id's 
 * @author Pieter
 */

public interface ShiftItemJpaRepository extends JpaRepository<ShiftItem, Long> {

    /**
     * Jpa Query which returns a ShiftItem whose {@link Long} id (from {@link Item}) matches the id parameter and whose {@link Shift} matches the shift parameter
     * @param id    {@link Long}
     * @param shift {@link Shift}
     * @return {@link ShiftItem}
     */
    @Query("SELECT s FROM ShiftItem s WHERE s.item.id = :id AND s.shift = :shift")
    public ShiftItem findActiveShiftItemWithItemId(@Param("id") Long id, @Param("shift") Shift shift);

}