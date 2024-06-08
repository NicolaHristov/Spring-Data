package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Volcano;

import java.util.Optional;
import java.util.Set;

@Repository
public interface VolcanoRepository extends JpaRepository<Volcano,Long> {

    Optional<Volcano> findByName(String name);

//•	Filter only volcanoes that are active with an elevation of more than 3000m. and have information about the last eruption.
// Order the results descending by elevation•

//    Set<Volcano>findAllByActiveIsTrueAndElevationGreaterThanAndLastEruptionIsNotEmptyOrderByElevationDesc(int elevation);
//    Set<Volcano>findAllByActiveIsTrueAndElevationGreaterThanAndLastEruptionIsNotNullOrderByElevationDesc(int elevation);

//    Set<Volcano>findByElevationGreaterThanAndActiveIsTrueAndLastEruptionIsNotNullOrderByElevationDesc(int elevation);

    @Query(value = "SELECT * FROM volcanoes WHERE elevation > 3000" +
            " AND last_eruption IS NOT NULL AND is_active = 1 ORDER BY elevation DESC", nativeQuery = true)
    Set<Volcano>findByElevationGreaterThanAndActiveIsTrueAndLastEruptionIsNotNullOrderByElevationDesc();


//    Set<Volcano>



}
