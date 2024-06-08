package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Customer;
import bg.softuni.cardealerexercise.data.repository.CustomRepository;
import bg.softuni.cardealerexercise.service.CustomService;
import bg.softuni.cardealerexercise.service.dtos.exports.CustomerOrderDto;
import bg.softuni.cardealerexercise.service.dtos.exports.CustomerOrderRootDto;
import bg.softuni.cardealerexercise.service.dtos.exports.totalSales5.CustomerTotalSalesDto;
import bg.softuni.cardealerexercise.service.dtos.exports.totalSales5.CustomersTotalSalesRootDto;
import bg.softuni.cardealerexercise.service.dtos.imports.CustomerSeedRootDto;
import bg.softuni.cardealerexercise.util.ValidationUtil;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomService {
    private static final String CUSTOMERS_PATH = "src/main/resources/xml/imports/customers.xml";
    private static final String EXPORT_CUSTOMERS = "src/main/resources/xml/exports/ordered-customers.xml";
    private static final String EXPORT_TOTAL_SALES = "src/main/resources/xml/exports/customers-total-sales.xml";
    private static final String EXPORT_DELETE = "src/main/resources/xml/exports/training-customers-for-delete.xml";

    private final CustomRepository customRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public CustomerServiceImpl(CustomRepository customRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.customRepository = customRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedCustomers() throws JAXBException {
        if(customRepository.count() == 0){
            CustomerSeedRootDto customerSeedRootDto = xmlParser.fromFile(CustomerSeedRootDto.class, CUSTOMERS_PATH);

            customerSeedRootDto.getCustomerSeedDtoList().forEach( cus ->
                    customRepository.saveAndFlush(modelMapper.map(cus, Customer.class)));

            System.out.println();
        }

    }

    @Override
    public void exportOrderedCustomers() throws JAXBException {
        List<CustomerOrderDto> customerOrderDtos = customRepository.findAllByOrderByBirthdateAscIsYoungDriver().stream()
                .map(cus -> modelMapper.map(cus, CustomerOrderDto.class))
                .collect(Collectors.toList());

        CustomerOrderRootDto customerOrderRootDto =new CustomerOrderRootDto();
        customerOrderRootDto.setCustomerOrderDtoList(customerOrderDtos);

        xmlParser.exportToFile(CustomerOrderRootDto.class,customerOrderRootDto,EXPORT_CUSTOMERS);
    }

    @Override
    public void exportCustomersWithBoughtCars() throws JAXBException {
        List<CustomerTotalSalesDto> customerTotalSalesDtoList = customRepository.findAllWithBoughtCars().stream().map(cus -> {
            CustomerTotalSalesDto customerTotalSalesDto = new CustomerTotalSalesDto();
            customerTotalSalesDto.setFullName(cus.getName());
            customerTotalSalesDto.setBoughtCars(cus.getSales().size());

            double spentMoney = cus.getSales().stream().mapToDouble
                    (sal -> sal.getCar().getParts().stream().mapToDouble(part -> part.getPrice().doubleValue()).sum()).sum();

            customerTotalSalesDto.setSpentMoney(BigDecimal.valueOf(spentMoney));
            return customerTotalSalesDto;
        }).sorted(Comparator.comparing(CustomerTotalSalesDto::getSpentMoney).reversed().
                thenComparing(Comparator.comparing(CustomerTotalSalesDto::getBoughtCars).reversed())).collect(Collectors.toList());

        CustomersTotalSalesRootDto customersTotalSalesRootDto = new CustomersTotalSalesRootDto();
        customersTotalSalesRootDto.setCustomerTotalSalesDtoList(customerTotalSalesDtoList);

        xmlParser.exportToFile(CustomersTotalSalesRootDto.class,customersTotalSalesRootDto,EXPORT_DELETE);
    }

//    @Override
//    public void exportCustomersWithBoughtCars() throws JAXBException {
//        List<CustomerTotalSalesDto> customerTotalSalesDtoList = customRepository.findAllWithBoughtCars().stream().map(cus -> {
//            CustomerTotalSalesDto customerTotalSalesDto = new CustomerTotalSalesDto();
//            customerTotalSalesDto.setFullName(cus.getName());
//            customerTotalSalesDto.setBoughtCars(cus.getSales().size());
//
//            double spentMoney = cus.getSales().stream().
//                    mapToDouble(sal -> sal.getCar().getParts().stream().mapToDouble(part -> part.getPrice().doubleValue()).sum() * sal.getDiscount()).sum();
//
//            customerTotalSalesDto.setSpentMoney(BigDecimal.valueOf(spentMoney));
//
//            return customerTotalSalesDto;
//        }).sorted(Comparator.comparing(CustomerTotalSalesDto::getSpentMoney).reversed()
//           .thenComparing(Comparator.comparing(CustomerTotalSalesDto::getBoughtCars).reversed())).collect(Collectors.toList());
//
//        CustomersTotalSalesRootDto customersTotalSalesRootDto = new CustomersTotalSalesRootDto();
//        customersTotalSalesRootDto.setCustomerTotalSalesDtoList(customerTotalSalesDtoList);
//
//        xmlParser.exportToFile(CustomersTotalSalesRootDto.class,customersTotalSalesRootDto,EXPORT_TOTAL_SALES);
//    }
}
