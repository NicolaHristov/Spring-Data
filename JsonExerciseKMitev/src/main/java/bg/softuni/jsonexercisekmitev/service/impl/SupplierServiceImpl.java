package bg.softuni.jsonexercisekmitev.service.impl;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Supplier;
import bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo.SupplierRepository;
import bg.softuni.jsonexercisekmitev.service.SupplierService;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.LocalSupplierDto;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.SupplierSeedDto;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.TotalSalesByCustomerDto;
import bg.softuni.jsonexercisekmitev.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String SUPPLIERS_FILE_PATH = "src/main/resources/files/suppliers.json";
    private static final String EXPORT_SUPPLIERS_FILE_PATH = "src/main/resources/files/export/local-suppliers.json";

    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSuppliers() throws IOException {
        if (supplierRepository.count() > 0) {
            return;
        }
        String fileContent = Files.readString(Path.of(SUPPLIERS_FILE_PATH));
        SupplierSeedDto[] supplierSeedDtos = gson.fromJson(fileContent, SupplierSeedDto[].class);

        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            if (!validationUtil.isValid(supplierSeedDto)) {
                validationUtil.getViolations(supplierSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }
            Supplier supplier = modelMapper.map(supplierSeedDto, Supplier.class);
//                supplierRepository.saveAllAndFlush(supplier);  НЕ МИ ГО ПРИЕМА SAVEANDFLUSH
            supplierRepository.save(supplier);
        }
    }

    @Override
    public void exportSupplier() throws IOException {
        List<LocalSupplierDto> localSupplierDtos = supplierRepository.findAllByIsImporter(false).stream()
                .map(supplier -> {
                    LocalSupplierDto dto = modelMapper.map(supplier, LocalSupplierDto.class);
                    dto.setPartsCount(supplier.getParts().size());

                    return dto;
                }).toList();

        gson.toJson(localSupplierDtos,new FileWriter(EXPORT_SUPPLIERS_FILE_PATH));
        System.out.println();

    }

//    @Override
//    public void exportTotalSalesByCustomer() {
//        //Get all customers that have bought at least 1 car and get their names, count of cars bought and total money spent on cars.
//        supplierRepository.findAll().stream().filter(cus -> !cus.getParts().isEmpty()).
//                map(customer ->{
//
//                    TotalSalesByCustomerDto dto = modelMapper.map(customer, TotalSalesByCustomerDto.class);
//                    dto.setFullName(customer.getName());
//                    dto.setName(String.format("%s %s",customer.getName()));
//                     customer.se
//
//
//
//
//                })
//    }
}
