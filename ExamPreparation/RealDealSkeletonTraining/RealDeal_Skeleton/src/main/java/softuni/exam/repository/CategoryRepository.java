package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.Categories;

//ToDo
public interface CategoryRepository extends JpaRepository<Categories,Long> {
}
