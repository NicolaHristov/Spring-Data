package bg.softuni.jsonexercisekmitev.service.impl;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Car;
import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Customer;
import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Part;
import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Sale;
import bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo.CarRepository;
import bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo.CustomerRepository;
import bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo.SaleRepository;
import bg.softuni.jsonexercisekmitev.service.SaleService;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.CarSaleWithDiscountDto;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.SaleDiscountDto;
import bg.softuni.jsonexercisekmitev.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private static final String SUPPLIERS_FILE_PATH = "src/main/resources/files/suppliers.json";
    private static final String EXPORT_SALE_SUPPLIERS_FILE_PATH = "src/main/resources/files/export/sales-discounts.json";
    private static final String SECOND_EXPORT_SALE_SUPPLIERS_FILE_PATH = "src/main/resources/files/export/second-sale-discount.json";
    private final List<Double> discountsList = List.of(1.0,0.95,0.9,0.85,0.8,0.7,0.6,0.5);
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public SaleServiceImpl(CarRepository carRepository, CustomerRepository customerRepository, SaleRepository saleRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSales() {
        if(saleRepository.count() == 0){
            for (int i = 0; i <50 ; i++) {
                Sale sale = new Sale();
                sale.setCar(getRandomCar());
                sale.setCustomer(getRandomCustomer());
                sale.setDiscount(getRandomDiscount());
                saleRepository.saveAndFlush(sale);

            }
        }
    }

    @Override
    public void exportSalesWithDiscount() throws IOException {
        List<SaleDiscountDto> saleDiscountDtos = saleRepository.findAll().stream().map(sale -> {
            SaleDiscountDto dto = modelMapper.map(sale, SaleDiscountDto.class);
            CarSaleWithDiscountDto carSaleWithDiscountDto = modelMapper.map(sale.getCar(), CarSaleWithDiscountDto.class);

            dto.setCarSaleWithDiscountDto(carSaleWithDiscountDto);
            dto.setCustomerName(sale.getCustomer().getName());

            double sum = sale.getCar().getParts().stream().mapToDouble(part ->part.getPrice().doubleValue()).sum();
            dto.setPrice(BigDecimal.valueOf(sum));
//            dto.setPrice(sale.getCar().getParts().stream().map(Part::getPrice).reduce(BigDecimal::add).get());
            dto.setPriceWithDiscount(dto.getPrice().multiply(BigDecimal.valueOf(sale.getDiscount())));

            return dto;
        }).toList();

        gson.toJson(saleDiscountDtos,new FileWriter(SECOND_EXPORT_SALE_SUPPLIERS_FILE_PATH));
    }

    private double getRandomDiscount() {
          return discountsList.get(ThreadLocalRandom.current().nextInt(1,discountsList.size()));
    }

    private Customer getRandomCustomer() {
        return customerRepository.findById(ThreadLocalRandom.current().nextLong(1,customerRepository.count()+1)).get();
    }

    private Car getRandomCar() {
        return carRepository.findById(ThreadLocalRandom.current().nextLong(1,carRepository.count()+1)).get();
    }
}
