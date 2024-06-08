package softuni.exam.service;

import softuni.exam.models.entity.Volcano;

import java.io.IOException;
import java.util.Optional;

// TODO: Implement all methods
public interface VolcanoService {

    boolean areImported();

    String readVolcanoesFileContent() throws IOException;

    String importVolcanoes() throws IOException;
    Optional<Volcano> findByName(String name);

//    Volcano findVolcanoById(Long volcanoId);
//
//    void addAndSaveAddedVolcano(Volcano volcano, Volcanologist volcanologist);

    String exportVolcanoes();

    Volcano findVolcanoByid(Long volcanoId);
}
