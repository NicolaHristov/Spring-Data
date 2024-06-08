package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Car;
import bg.softuni.cardealerexercise.data.entities.Customer;
import bg.softuni.cardealerexercise.data.entities.Part;
import bg.softuni.cardealerexercise.data.entities.Sale;
import bg.softuni.cardealerexercise.data.repository.CarRepository;
import bg.softuni.cardealerexercise.data.repository.CustomRepository;
import bg.softuni.cardealerexercise.data.repository.SaleRepository;
import bg.softuni.cardealerexercise.service.SaleService;
import bg.softuni.cardealerexercise.service.dtos.exports.totalSales5.CarDto;
import bg.softuni.cardealerexercise.service.dtos.exports.totalSales5.SaleDiscountDto;
import bg.softuni.cardealerexercise.service.dtos.exports.totalSales5.SaleDiscountRootDto;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private static final String EXPORT_SALE = "src/main/resources/xml/exports/sales-discounts.xml";

    private final List<Double> discountsList = List.of(1.0,0.95,0.9,0.85,0.8,0.7,0.6,0.5);

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomRepository customRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomRepository customRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customRepository = customRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
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
    public void exportSalesWithDiscount() throws JAXBException {
        List<SaleDiscountDto> saleDiscountDtos = saleRepository.findAll().stream().map(sale -> {
            SaleDiscountDto saleDiscountDto = modelMapper.map(sale, SaleDiscountDto.class);
            CarDto carDto = modelMapper.map(sale.getCar(), CarDto.class);

            saleDiscountDto.setCarDto(carDto);
            saleDiscountDto.setCustomerName(sale.getCustomer().getName());
            saleDiscountDto.setPrice(sale.getCar().getParts().stream().map(Part::getPrice).reduce(BigDecimal::add).get());
            saleDiscountDto.setPriceWithDiscount(saleDiscountDto.getPrice().multiply(BigDecimal.valueOf(sale.getDiscount())));

            return saleDiscountDto;
        }).collect(Collectors.toList());

        SaleDiscountRootDto saleDiscountRootDto = new SaleDiscountRootDto();
        saleDiscountRootDto.setSaleDiscountDtoList(saleDiscountDtos);

        xmlParser.exportToFile(SaleDiscountRootDto.class,saleDiscountRootDto,EXPORT_SALE);
    }

    private double getRandomDiscount() {
        return discountsList.get(ThreadLocalRandom.current().nextInt(1,discountsList.size()));
    }

    private Customer getRandomCustomer() {
        return customRepository.findById(ThreadLocalRandom.current().nextLong(1,customRepository.count() + 1)).get();
    }

    private Car getRandomCar() {
        return carRepository.findById(ThreadLocalRandom.current().nextLong(1,carRepository.count() + 1)).get();
    }
}
