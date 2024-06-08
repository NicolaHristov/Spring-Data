package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Part;
import bg.softuni.cardealerexercise.data.entities.Supplier;
import bg.softuni.cardealerexercise.data.repository.PartRepository;
import bg.softuni.cardealerexercise.data.repository.SupplierRepository;
import bg.softuni.cardealerexercise.service.PartService;
import bg.softuni.cardealerexercise.service.dtos.imports.PartSeedDto;
import bg.softuni.cardealerexercise.service.dtos.imports.PartSeedRootDto;
import bg.softuni.cardealerexercise.util.ValidationUtil;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {


    private static final String PARTS_PATH = "src/main/resources/xml/imports/parts.xml";
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedParts() throws JAXBException {
        if(partRepository.count() == 0){
            PartSeedRootDto partSeedRootDto = xmlParser.fromFile(PartSeedRootDto.class, PARTS_PATH);

            for (PartSeedDto partSeedDto : partSeedRootDto.getPartSeedDtoList()) {
                if(!validationUtil.isValid(partSeedDto)){
                    System.out.println("Invalid data");
                    continue;
                }
                Part part = modelMapper.map(partSeedDto,Part.class);
                part.setSupplier(geRandomSupplier());
                partRepository.saveAndFlush(part);
            }
        }
    }

    private Supplier geRandomSupplier() {
        return this.supplierRepository.findById
        (ThreadLocalRandom.current().nextLong(1,supplierRepository.count() + 1)).get();
    }
}
