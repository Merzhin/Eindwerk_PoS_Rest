package be.livingsmart.eindwerk;


import be.livingsmart.hdr.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *  Jpa specific extension of Repository, using {@link Shift}s and {@link Long} id's 
 * @author Pieter
 */
public interface ShiftJpaRepository extends JpaRepository<Shift, Long> {

    /**
     *  Returns the active {@link Shift} using a Query
     * @return  active {@link Shift}
     */
    @Query("SELECT s FROM Shift s WHERE s.endTime = null")
    public Shift findActiveShift();
}
