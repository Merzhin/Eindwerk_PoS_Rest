package be.livingsmart.eindwerk;


import be.livingsmart.eindwerk.domain.ShiftItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShiftItemJpaRepository extends JpaRepository<ShiftItem, Long> {

    @Query("SELECT s FROM ShiftItem s WHERE s.item.id = :id")
    public ShiftItem findShiftItemWithItemId(@Param("id") Long id);
}