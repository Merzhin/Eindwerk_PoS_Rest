package be.livingsmart.eindwerk;


import be.livingsmart.eindwerk.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftJpaRepository extends JpaRepository<Shift, Long> {

}
