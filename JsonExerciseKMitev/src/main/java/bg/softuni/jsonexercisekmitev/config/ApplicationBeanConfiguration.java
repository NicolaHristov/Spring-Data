package bg.softuni.jsonexercisekmitev.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

//    @Bean
//    public ModelMapper modelMapper(){
//        return new ModelMapper();
//    }
@Bean
public ModelMapper modelMapper(){
    ModelMapper modelMapper= new ModelMapper();

    modelMapper.addConverter((Converter<String, LocalDate>) mappingContext -> LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

    modelMapper.addConverter((Converter<String, LocalDateTime>) mappingContext -> {
        return LocalDateTime.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    });

    modelMapper.addConverter((Converter<LocalDateTime, String>) mappingContext ->
            mappingContext.getSource().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    modelMapper.addConverter((Converter<LocalDate, String>) mappingContext ->
            mappingContext.getSource().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


    return modelMapper;
}

    @Bean
    public Gson gson(){
        return new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
