package be.livingsmart.eindwerk;

import domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftJpaRepository extends JpaRepository<Shift, Long> {

}
