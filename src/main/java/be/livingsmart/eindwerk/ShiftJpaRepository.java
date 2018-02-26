package be.livingsmart.eindwerk;


import be.livingsmart.eindwerk.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShiftJpaRepository extends JpaRepository<Shift, Long> {

    @Query("SELECT s FROM Shift s WHERE s.endTime = null")
    public Shift findActiveShift();
}
