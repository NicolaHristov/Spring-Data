package bg.softuni.jsonexercisekmitev.service.impl;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Customer;
import bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo.CustomerRepository;
import bg.softuni.jsonexercisekmitev.service.CustomerService;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.CustomerDto;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.CustomerOrderDto;
import bg.softuni.jsonexercisekmitev.service.dtos.CarDealerDto.TotalSalesByCustomerDto;
import bg.softuni.jsonexercisekmitev.util.ValidationUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_PATH = "src/main/resources/files/customers.json";
    private static final String EXPORT_CUSTOMER_PATH = "src/main/resources/files/export/ordered-customers.json";
    private static final String TOTAL_SALES_EXPORT_CUSTOMER_PATH = "src/main/resources/files/export/customers-total-sales.json";

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedCustomers() throws IOException {
         String fileContent = Files.readString(Path.of(CUSTOMER_PATH));
        CustomerDto[] customerDtos = gson.fromJson(fileContent, CustomerDto[].class);

        for (CustomerDto customerDto : customerDtos) {
            if(!validationUtil.isValid(customerDto)){
                validationUtil.getViolations(customerDto).forEach(vio -> System.out.println(vio.getMessage()));
                continue;
            }
            Customer customer = modelMapper.map(customerDto,Customer.class);
            customerRepository.save(customer);
        }
    }

    @Override
    public void exportOrderedCustomers()   {
//        List<CustomerOrderDto[]> customerOrderDtos = customerRepository.findAllByOrderByBirthdateAscIsYoungDriver().stream()
//                .map(customer -> modelMapper.map(customer, CustomerOrderDto[].class)).toList();
        List<CustomerOrderDto> customerOrderDtos = customerRepository.findAllByOrderByBirthdateAscIsYoungDriver().stream()
                .map(customer -> modelMapper.map(customer, CustomerOrderDto.class)).toList();


        saveToJsonFile(customerOrderDtos,EXPORT_CUSTOMER_PATH);
    }

    @Override
    public void exportCustomersWithBoughtCars() throws IOException {
        List<TotalSalesByCustomerDto> totalSalesByCustomerDtos = customerRepository.findAll().stream().filter(cus -> !cus.getSales().isEmpty())
                .map(customer -> {
                    TotalSalesByCustomerDto dto = modelMapper.map(customer, TotalSalesByCustomerDto.class);
                    dto.setFullName(customer.getName());
                    dto.setBoughtCars(customer.getSales().size());
                    double sum = customer.getSales().stream().mapToDouble(sal -> sal.getCar().getParts().stream()
                            .mapToDouble(part -> part.getPrice().doubleValue()).sum()).sum();
                    dto.setSpentMoney(BigDecimal.valueOf(sum));
                    return dto;
                }).sorted(Comparator.comparing(TotalSalesByCustomerDto::getSpentMoney).reversed()
                        .thenComparing(TotalSalesByCustomerDto::getBoughtCars).reversed()).toList();
//. Order the result list by total money spent in descending order then by total cars bought again in descending order.
                  gson.toJson(totalSalesByCustomerDtos,new FileWriter(TOTAL_SALES_EXPORT_CUSTOMER_PATH));

    }

    private void saveToJsonFile(List<CustomerOrderDto> customerOrderDtos, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(customerOrderDtos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
