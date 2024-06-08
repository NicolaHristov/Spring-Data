//package softuni.exam.service.impl;
//
//import com.google.gson.Gson;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//import softuni.exam.models.dto.SellerDto;
//import softuni.exam.models.entity.Seller;
//import softuni.exam.repository.SellerRepository;
//import softuni.exam.service.SellerService;
//import softuni.exam.util.ValidationUtil;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Arrays;
//import java.util.Optional;
//
//@Service
//public class SellerServiceImpl implements SellerService {
//    private static final String SELLERS_FILE_PATH = "src/test/resources/original-files/sellers.json";
//    private final SellerRepository sellerRepository;
//    private final Gson gson;
//    private final ModelMapper modelMapper;
//    private final ValidationUtil validationUtil;
//
//    public SellerServiceImpl(SellerRepository sellerRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
//        this.sellerRepository = sellerRepository;
//        this.gson = gson;
//        this.modelMapper = modelMapper;
//        this.validationUtil = validationUtil;
//    }
//
//    @Override
//    public boolean areImported() {
//        return sellerRepository.count() > 0;
//    }
//
//    @Override
//    public String readSellersFromFile() throws IOException {
//        return Files.readString(Path.of(SELLERS_FILE_PATH));
//    }
//
////    @Override
////    public String importSellers() throws IOException {
////        StringBuilder build = new StringBuilder();
////
////        Arrays.stream(gson.fromJson(readSellersFromFile(), SellerDto[].class))
////                .filter(sellerSeedDto -> {
////                    boolean isValid = validationUtil.isValid(sellerSeedDto);
////
////                    Optional<Seller> sellerByName = sellerRepository
////                            .findSellerByLastName(sellerSeedDto.getLastName());
////
////                    Optional<Seller> sellerByNumber = sellerRepository
////                            .findSellerByPersonalNumber(sellerSeedDto.getPersonalNumber());
////
////
////
////                    if (sellerByName.isPresent() || sellerByNumber.isPresent()) {
////                        isValid = false;
////                    }
//////
//////                    Seller seller = sellerRepository.findSellerByFirstNameAndLastName(sellerSeedDto.getFirstName(),
//////                            sellerSeedDto.getLastName()).orElse(null);
//////                    if (seller != null) {
//////                        isValid = false;
//////                    }
////
////                    build.append(isValid
////                                    ? String.format("Successfully imported seller %s %s",
////                                    sellerSeedDto.getFirstName(),
////                                    sellerSeedDto.getLastName())
////                                    : "Invalid seller")
////                            .append(System.lineSeparator());
////
////                    return isValid;
////                })
////                .map(sellerSeedDto -> modelMapper.map(sellerSeedDto, Seller.class))
////                .forEach(sellerRepository::save);
////
////        return build.toString().trim();
////    }
//
//    @Override
//    public String importSellers() throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        SellerDto[] sellerDtos = gson.fromJson(readSellersFromFile(), SellerDto[].class);
//
//        for (SellerDto sellerDto : sellerDtos) {
//            //If a seller with the same last name already exists in the DB or the first/last name does not meet size constraints return "Invalid seller".
//            Optional<Seller> optionalSeller = sellerRepository.findSellerByLastName(sellerDto.getLastName());
//            Optional<Seller> sellerByNumber = sellerRepository
//                    .findSellerByPersonalNumber(sellerDto.getPersonalNumber());
//
////            boolean isFirstNameValid = validationUtil.isValid(sellerDto.getFirstName());
////            boolean isLastNameValid = validationUtil.isValid(sellerDto.getLastName());
//            boolean isValid = validationUtil.isValid(sellerDto) && optionalSeller.isEmpty() && sellerByNumber.isEmpty();
//
//            stringBuilder.append(isValid ? String.format("Successfully imported seller %s %s"
//                    ,sellerDto.getFirstName(),sellerDto.getLastName()) : "Invalid seller").append(System.lineSeparator());
//
//            if(isValid){
//                Seller seller = modelMapper.map(sellerDto, Seller.class);
//                sellerRepository.save(seller);
//            }
//
//        }
//        return stringBuilder.toString();
//    }
//
//    @Override
//    public void saveAddedSaleToSeller(Seller seller) {
//        sellerRepository.save(seller);
//    }
//
//    @Override
//    public Seller findSellerById(long seller) {
//      return  sellerRepository.findById(seller).orElse(null);
//    }
//}

package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SellerDto;

import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    private static final String SELLER_FILE_PATH = "src/main/resources/files/json/sellers.json";

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final SellerRepository sellerRepository;

    public SellerServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, SellerRepository sellerRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.sellerRepository = sellerRepository;
    }


    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files
                .readString(Path.of(SELLER_FILE_PATH));
    }

//    @Override
//    public String importSellers() throws IOException {
//        StringBuilder build = new StringBuilder();
//
//        Arrays.stream(gson.fromJson(readSellersFromFile(), SellerDto[].class))
//                .filter(sellerSeedDto -> {
//
//                    Optional<Seller> sellerByName = sellerRepository
//                            .findSellerByLastName(sellerSeedDto.getLastName());
//
//                    Optional<Seller> sellerByNumber = sellerRepository
//                            .findSellerByPersonalNumber(sellerSeedDto.getPersonalNumber());
//
//                    boolean isValid = validationUtil.isValid(sellerSeedDto) && sellerByNumber.isEmpty() && sellerByName.isEmpty();
//
//
////                    if (sellerByName.isPresent() || sellerByNumber.isPresent()) {
////                        isValid = false;
////                    }
//////
////                    Seller seller = sellerRepository.findSellerByFirstNameAndLastName(sellerSeedDto.getFirstName(),
////                            sellerSeedDto.getLastName()).orElse(null);
////                    if (seller != null) {
////                        isValid = false;
////                    }
//
//                    build.append(isValid
//                                    ? String.format("Successfully imported seller %s %s",
//                                    sellerSeedDto.getFirstName(),
//                                    sellerSeedDto.getLastName())
//                                    : "Invalid seller")
//                            .append(System.lineSeparator());
//
//                    return isValid;
//                })
//                .map(sellerSeedDto -> modelMapper.map(sellerSeedDto, Seller.class))
//                .forEach(sellerRepository::save);
//
//        return build.toString().trim();
//    }
        @Override
    public String importSellers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        SellerDto[] sellerDtos = gson.fromJson(readSellersFromFile(), SellerDto[].class);

        for (SellerDto sellerDto : sellerDtos) {
            //If a seller with the same last name already exists in the DB or the first/last name does not meet size constraints return "Invalid seller".
            Optional<Seller> optionalSeller = sellerRepository.findSellerByLastName(sellerDto.getLastName());
//            Optional<Seller> sellerByNumber = sellerRepository
//                    .findSellerByPersonalNumber(sellerDto.getPersonalNumber());

//            boolean isFirstNameValid = validationUtil.isValid(sellerDto.getFirstName());
//            boolean isLastNameValid = validationUtil.isValid(sellerDto.getLastName());
//            boolean isValid = validationUtil.isValid(sellerDto) && optionalSeller.isEmpty() && sellerByNumber.isEmpty();
            boolean isValid = validationUtil.isValid(sellerDto) && optionalSeller.isEmpty();

            stringBuilder.append(isValid ? String.format("Successfully imported seller %s %s"
                    ,sellerDto.getFirstName(),sellerDto.getLastName()) : "Invalid seller").append(System.lineSeparator());

            if(isValid){
                Seller seller = modelMapper.map(sellerDto, Seller.class);
                sellerRepository.save(seller);
            }

        }
        return stringBuilder.toString();
    }

    @Override
    public Seller findSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId).orElse(null);
    }

    @Override
    public void saveAddedSaleToSeller(Seller seller) {
        sellerRepository.save(seller);
    }

    @Override
    public Seller findSellerById(long seller) {
        return sellerRepository.findById(seller).orElse(null);
    }


}
