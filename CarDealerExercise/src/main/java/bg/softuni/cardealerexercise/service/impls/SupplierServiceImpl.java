package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Supplier;
import bg.softuni.cardealerexercise.data.repository.SupplierRepository;
import bg.softuni.cardealerexercise.service.SupplierService;
import bg.softuni.cardealerexercise.service.dtos.exports.SupplierLocalDto;
import bg.softuni.cardealerexercise.service.dtos.exports.SupplierLocalRootDto;
import bg.softuni.cardealerexercise.service.dtos.imports.SupplierSeedDto;
import bg.softuni.cardealerexercise.service.dtos.imports.SupplierSeedRootDto;
import bg.softuni.cardealerexercise.util.ValidationUtil;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String SUPPLIERS_FILE_PATH = "src/main/resources/xml/imports/suppliers.xml";
    private static final String EXPORT_FILE_PATH = "src/main/resources/xml/exports/local-suppliers.xml";

    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;


    public SupplierServiceImpl(SupplierRepository supplierRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSuppliers() throws JAXBException {
        if(supplierRepository.count() == 0){
            SupplierSeedRootDto supplierSeedRootDto = xmlParser.fromFile(SupplierSeedRootDto.class, SUPPLIERS_FILE_PATH);

            for (SupplierSeedDto supplierSeedDto : supplierSeedRootDto.getSupplierSeedDtoList()) {
                if(!validationUtil.isValid(supplierSeedDto)){
                    validationUtil.getViolations(supplierSeedDto)
                            .forEach( v -> System.out.println(v.getMessage()));
                    continue;
                }
                Supplier supplier = modelMapper.map(supplierSeedDto,Supplier.class);
//                supplierRepository.saveAllAndFlush(supplier);  НЕ МИ ГО ПРИЕМА SAVEANDFLUSH
                supplierRepository.save(supplier);
            }
        }

        System.out.println();
    }

    @Override
    public void extractLocalSupplier() throws JAXBException {

        List<SupplierLocalDto> supplierLocalDtos = supplierRepository.findAllByIsImporter(false).
                stream().map(supp -> {
                    SupplierLocalDto supplierLocalDto = modelMapper.map(supp,SupplierLocalDto.class);
                    supplierLocalDto.setPartsCount(supp.getParts().size());
                    return supplierLocalDto;
                })
                .collect(Collectors.toList());

        SupplierLocalRootDto supplierLocalRootDto = new SupplierLocalRootDto();
        supplierLocalRootDto.setSupplierLocalDtoList(supplierLocalDtos);

       xmlParser.exportToFile(SupplierLocalRootDto.class,supplierLocalRootDto,EXPORT_FILE_PATH);
    }
}
