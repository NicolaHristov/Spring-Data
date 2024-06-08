package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast,Long> {

   Optional<Forecast>  findAllByCityAndDayOfWeek(City city, DayOfWeek dayOfWeek);

//   Optional<Set<Forecast>>  findAllByDayOfWeekAndCityLessThanOrderByMaxTemperatureDescIdAsc(DayOfWeek dayOfWeek, Long number);


   Optional<Set<Forecast>>  findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DayOfWeek dayOfWeek, Long number);



}
