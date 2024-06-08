package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.DeviceDto;
import softuni.exam.models.dto.DevicesRootDto;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.DeviceType;
import softuni.exam.models.entity.Sale;
import softuni.exam.repository.DeviceRepository;
import softuni.exam.service.DeviceService;
import softuni.exam.service.SaleService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final String DEVISES_FILE_PATH = "src/main/resources/files/xml/devices.xml";
    private final DeviceRepository deviceRepository;
    private final SaleService saleService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public DeviceServiceImpl(DeviceRepository deviceRepository, SaleService saleService, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.deviceRepository = deviceRepository;
        this.saleService = saleService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return deviceRepository.count() > 0;
    }

    @Override
    public String readDevicesFromFile() throws IOException {
        return Files.readString(Path.of(DEVISES_FILE_PATH));
    }

//    @Override
//    public String importDevices() throws IOException, JAXBException {
//
//        StringBuilder build = new StringBuilder();
//
//        xmlParser
//                .fromFile(DEVISES_FILE_PATH, DevicesRootDto.class)
//                .getDeviceDtoList()
//                .stream()
//                .filter(deviceSeedDto -> {
//                    boolean isValid = validationUtil.isValid(deviceSeedDto);
//
//
//                    Sale sale = saleService.findSaleById(deviceSeedDto.getSale());
//
//                    if (sale == null) {
//                        isValid = false;
//                    }
//
//
//                    Device device = deviceRepository.findDeviceByModel(deviceSeedDto.getModel())
//                            .orElse(null);
//                    if (device != null) {
//                        isValid = false;
//                    }
//
//                    build
//                            .append(isValid
//                                    ? String.format("Successfully imported device of type %s with brand %s",
//                                    deviceSeedDto.getDeviceType(),
//                                    deviceSeedDto.getBrand())
//                                    : "Invalid device")
//                            .append(System.lineSeparator());
//
//                    return isValid;
//                })
//                .map(deviceSeedDto -> {
//
//                    Device device = modelMapper.map(deviceSeedDto, Device.class);
//
//                    Sale sale = saleService.findSaleById(deviceSeedDto.getSale());
//                    saleService.addAndSaveAddedDevice(sale, device);
//
//                    device.setSale(sale);
//                    return device;
//
//                })
//                .forEach(deviceRepository::save);
//
//        return build.toString();
//    }

    @Override
    public String importDevices() throws IOException, JAXBException {
//        DevicesRootDto[] devicesRootDtos = xmlParser.fromFile(readDevicesFromFile(), DevicesRootDto[].class);
//        DevicesRootDto devicesRootDto = xmlParser.fromFile(DEVISES_FILE_PATH(), DevicesRootDto.class);
        DevicesRootDto devicesRootDto = xmlParser.fromFile(DEVISES_FILE_PATH, DevicesRootDto.class);
        StringBuilder stringBuilder = new StringBuilder();

        for (DeviceDto deviceDto : devicesRootDto.getDeviceDtoList()) {
//•	If a device with the same brand and model already exists in the DB return "Invalid device".
//•	If a device appears in a sale that doesn't exist in the DB return "Invalid device
//Ако в продажба се появи устройство, което не съществува в DB, се връща „Невалидно устройство
//            Optional<Device> optionalDevice = deviceRepository.findAllByBrandAndModel(deviceDto.getBrand(),deviceDto.getModel());
            Device optionalDevice = deviceRepository.findDeviceByModel(deviceDto.getModel()).orElse(null);

            Sale optionalSale = saleService.findSaleById(deviceDto.getSale());

            boolean isValid = validationUtil.isValid(deviceDto);

            if(optionalDevice != null){
                isValid = false;
            }

            if(optionalSale == null){
                isValid = false;
            }

            stringBuilder.append(isValid ? String.format("Successfully imported device of type %s with brand %s",
                    deviceDto.getDeviceType(),deviceDto.getBrand()) : "Invalid device").append(System.lineSeparator());

            if(isValid){
                Device device = modelMapper.map(deviceDto, Device.class);
                Sale sale = saleService.findSaleById(deviceDto.getSale());
                Set<Device> devicosToAdd = new HashSet<>();
                devicosToAdd.add(device);
                sale.setDevices(devicosToAdd);
//                saleService.addAndSaveAddedDevice(sale, device);

                device.setSale(sale);

                deviceRepository.save(device);
            }


        }
        return stringBuilder.toString();
    }

//    @Override
//    public String exportDevices() {
//        StringBuilder stringBuilder = new StringBuilder();
//
//        List<Device> deviceList = deviceRepository.findDeviceByPriceLessThanAndDeviceTypeAndStorageGreaterThanEqualOrderByBrand(1000, DeviceType.SMART_PHONE, 128);
//
//        deviceList.forEach( device -> {
//            stringBuilder.append(String.format("Device brand: %s%n *Model: %s%n **Storage: %d%n ***Price: %.2f%n",device.getBrand(),
//             device.getModel(),device.getStorage(),device.getPrice())).append(System.lineSeparator());
//        });
////        deviceRepository.findDeviceByPriceLessThanAndDeviceTypeAndStorageGreaterThanEqualOrderByBrand(1000,DeviceType.SMART_PHONE,128)
////                .forEach(device -> {
////     stringBuilder.append(String.format("Device brand: %s%n *Model: %s%n **Storage: %d%n ***Price: %.2f",device.getBrand(),
////             device.getModel(),device.getStorage(),device.getPrice()
////             ));
////                });
//
////"Device brand: {brand}
////   *Model: {model}
////   **Storage: {storage}
////   ***Price: {price}
////. . ."
//        return stringBuilder.toString();
//    }

    @Override
    public String exportDevices() {
        StringBuilder build = new StringBuilder();

        List<Device> foundDevices = deviceRepository.findAllSmartPhonesCheaperThan1000AndStorageMoreThan128();

        foundDevices.forEach(v -> {
            build.append(String.format("Device brand: %s\n" +
                                    "   *Model: %s\n" +
                                    "   **Storage: %d\n" +
                                    "   ***Price: %.2f",
                            v.getBrand(),
                            v.getModel(),
                            v.getStorage(),
                            v.getPrice()))
                    .append(System.lineSeparator());

        });
        return build.toString();
    }
}






