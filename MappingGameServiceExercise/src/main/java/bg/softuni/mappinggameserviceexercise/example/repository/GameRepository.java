package bg.softuni.mappinggameserviceexercise.example.repository;

import bg.softuni.mappinggameserviceexercise.example.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

}
