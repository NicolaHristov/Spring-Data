package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureSeedDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURES_FILE_PATH = "src/main/resources/files/pictures.json";

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        //1.Как ще импортнем снимките - Трябва да си създадем необходимите ДТО - отварям си файлa pictures.json
        //Ще имам масив от оделни ДТО
        // Правя си PictureSeedDto - path,size
        //Трябва да подадем нашият масив към gson, Който се нужда от пътя(path) до определената директория и масива от ДТО-та в които да ни го превърне
//        PictureSeedDto[] pictureSeedDto = gson.fromJson(readFromFileContent(), PictureSeedDto[].class);
        StringBuilder stringBuilder = new StringBuilder();
         Arrays.stream(gson.fromJson(readFromFileContent(), PictureSeedDto[].class)).
                 filter(pictureSeedDto -> {
                     boolean isValid = validationUtil.isValid(pictureSeedDto) && !isEntityExist(pictureSeedDto.getPath());


                     stringBuilder.append(isValid ? String.format("Successfully import Picture,with size %.2f ",
                             pictureSeedDto.getSize()) : "Invalid picture").append(System.lineSeparator());

                     return isValid;
                 })
                 .map(pictureSeedDto -> modelMapper.map(pictureSeedDto, Picture.class))
                 .forEach(pictureRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public boolean isEntityExist(String path) {
       return pictureRepository.existsByPath(path);
    }

    @Override
    public String exportPictures() {
        return null;
    }

    @Override
    public Picture findByPath(String profilePicture) {
        return pictureRepository.findByPath(profilePicture).orElse(null);
    }
}
