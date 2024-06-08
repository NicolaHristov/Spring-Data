package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;

import java.util.Optional;
import java.util.Set;

@Repository
public interface StarRepository extends JpaRepository<Star,Long> {


    Optional<Star> findByName(String name);

//    Set<Star> findAllByStarTypeAndObserversIsEmptyOrdOrderByLightYearsAsc();


//    @Query("SELECT s FROM Star AS s WHERE s.starType = 'RED_GIANT' AND size(s.observers) = 0 ORDER BY s.lightYears")
    Set<Star> findAllByStarTypeAndObserversNullOrderByLightYears(StarType starType);
}
