package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {


//    @Bean
//    public ModelMapper modelMapper(){
//        ModelMapper modelMapper= new ModelMapper();
//
//        modelMapper.addConverter((Converter<String, LocalDate>) mappingContext ->
//        {
//            if (mappingContext.getSource() == null) {
//                return LocalDate.parse("00-00-00");
//            }
//            return LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        });
////                LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//
//        modelMapper.addConverter((Converter<String, LocalDateTime>) mappingContext -> {
//            if (mappingContext.getSource() == null) {
//                return LocalDateTime.parse("0000-00-00 00:11:22");
//            }
//            return LocalDateTime.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        });
////                LocalDateTime.parse(mappingContext.getSource(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//
//        modelMapper.addConverter((Converter<String, LocalTime>) mappingContext ->{
//            if (mappingContext.getSource() == null) {
//                return LocalTime.parse("11:22:33");
//            }
//            return LocalTime.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("HH:mm:ss"));
//        });
////                LocalTime.parse (mappingContext.getSource(),
////                DateTimeFormatter.ofPattern("HH:mm:ss")));
//
//        return modelMapper;
//    }
@Bean
public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    modelMapper.addConverter(new Converter<String, LocalDate>() {
        @Override
        public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {

            LocalDate parse = LocalDate
                    .parse(mappingContext.getSource(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            return parse;
        }
    });

    modelMapper.addConverter(new Converter<String, LocalDateTime>() {
        @Override
        public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
            LocalDateTime parse = LocalDateTime.parse(mappingContext.getSource(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return parse;
        }
    });

    modelMapper.addConverter(new Converter<String, LocalTime>() {
        @Override
        public LocalTime convert(MappingContext<String, LocalTime> mappingContext) {
            LocalTime parse = LocalTime.parse(mappingContext.getSource(),
                    DateTimeFormatter.ofPattern("HH:mm:ss"));
            return parse;
        }
    });

    return modelMapper;
}

    @Bean
    public Gson gson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }
}
