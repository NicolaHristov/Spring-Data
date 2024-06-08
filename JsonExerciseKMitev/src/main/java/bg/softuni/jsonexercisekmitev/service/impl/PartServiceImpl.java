package bg.softuni.jsonexercisekmitev.service.impl;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Part;
import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Supplier;
import bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo.PartRepository;
import bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo.SupplierRepository;
import bg.softuni.jsonexercisekmitev.service.PartService;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.PartDto;
import bg.softuni.jsonexercisekmitev.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {
    private static final String PATH = "src/main/resources/files/parts.json";
    private final PartRepository partRepository;

    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    public void seedParts() throws IOException {
        if(partRepository.count()>0){
            return;
        }
        String fileContent = Files.readString(Path.of(PATH));
        PartDto[] partDtos = gson.fromJson(fileContent, PartDto[].class);
        for (PartDto partDto : partDtos) {
            if (!validationUtil.isValid(partDto)) {
                validationUtil.getViolations(partDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }
            Part part = modelMapper.map(partDto,Part.class);
            part.setSupplier(geRandomSupplier());
            partRepository.saveAndFlush(part);
        }
    }

    private Supplier geRandomSupplier() {
//        long radnomid = ThreadLocalRandom.current().nextLong(1,supplierRepository.count()+1);
//        return supplierRepository.findById(radnomid).orElse(null); taka ne raboti

        return supplierRepository.findById(ThreadLocalRandom.current().nextLong(1,supplierRepository.count()+1)).orElse(null);
    }
}
