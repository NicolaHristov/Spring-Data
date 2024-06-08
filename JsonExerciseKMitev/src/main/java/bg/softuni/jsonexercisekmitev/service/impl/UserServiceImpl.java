package bg.softuni.jsonexercisekmitev.service.impl;

import bg.softuni.jsonexercisekmitev.data.entity.User;
import bg.softuni.jsonexercisekmitev.data.repository.UserRepository;
import bg.softuni.jsonexercisekmitev.service.UserService;
import bg.softuni.jsonexercisekmitev.service.dtos.ProductSoldDto;
import bg.softuni.jsonexercisekmitev.service.dtos.UserSeedDto;
import bg.softuni.jsonexercisekmitev.service.dtos.UserSoldProductsDto;
import bg.softuni.jsonexercisekmitev.service.dtos.dtoQuery4.ProductInfoDto;
import bg.softuni.jsonexercisekmitev.service.dtos.dtoQuery4.ProductSoldBuyerDto;
import bg.softuni.jsonexercisekmitev.service.dtos.dtoQuery4.UserAndProductDto;
import bg.softuni.jsonexercisekmitev.service.dtos.dtoQuery4.UserSoldDto;
import bg.softuni.jsonexercisekmitev.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String FILE_PATH = "src/main/resources/files/users.json";
    private final UserRepository userRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
         if(userRepository.count()>0){
             return;
         }
        UserSeedDto[]userSeedDtos = gson.fromJson(new FileReader(FILE_PATH), UserSeedDto[].class);
        for (UserSeedDto userSeedDto : userSeedDtos) {
            if(!validationUtil.isValid(userSeedDto)){
                validationUtil.getViolations(userSeedDto).forEach(val -> System.out.println(val.getMessage()));
                continue;
            }
            userRepository.saveAndFlush(modelMapper.map(userSeedDto, User.class));
        }
    }

    @Override
    public List<UserSoldProductsDto> getAllUsersAndSoldItem() {
        //Get all users who have at least 1 item sold with a buyer.
        //u -> !(u.getSold().stream().filter(p -> p.getBuyer() == null).count() == 0)
        return userRepository.findAll().stream().filter(u -> u.getSold().stream().anyMatch(product -> product.getBuyer() != null))
                .map(user -> {
                    UserSoldProductsDto userDto = modelMapper.map(user, UserSoldProductsDto.class);
                    List<ProductSoldDto> soldProductDto = user.getSold().stream().filter(product -> product.getBuyer() != null)
                            .map(product -> modelMapper.map(product, ProductSoldDto.class))
                            .collect(Collectors.toList());
                    userDto.setSoldProducts(soldProductDto);

                    return userDto;
                })
                .sorted(Comparator.comparing(UserSoldProductsDto::getLastName).thenComparing(UserSoldProductsDto::getFirstName))
                .collect(Collectors.toList());
    }

    @Override
    public void printAllUsersAndSoldProducts() {
        String json = gson.toJson(getAllUsersAndSoldItem());

        System.out.println(json);
    }

    @Override
    public UserAndProductDto getUsersAndProductsDto() {
        UserAndProductDto userAndProductDto = new UserAndProductDto();

        List<UserSoldDto> userSoldDtoList = userRepository.findAll().stream().filter(user -> !user.getSold().isEmpty())
                .map(user -> {
                    UserSoldDto userSoldDto = modelMapper.map(user, UserSoldDto.class);
                    ProductSoldBuyerDto productSoldBuyerDto = new ProductSoldBuyerDto();

                    List<ProductInfoDto> productInfoDtos = user.getSold().stream().map(product -> modelMapper.map(product, ProductInfoDto.class)).
                            collect(Collectors.toList());
                    productSoldBuyerDto.setProducts(productInfoDtos);
                    productSoldBuyerDto.setCount(productInfoDtos.size());
                    //    public void setSoldProducts(List<ProductSoldBuyerDto> soldProducts) {
                    //        this.soldProducts = soldProducts;
                    //    }

                    userSoldDto.setSoldProducts(productSoldBuyerDto);

                    return userSoldDto;
                }).sorted((a,b) -> {
                    int countA = a.getSoldProducts().getCount();
                    int countB = b.getSoldProducts().getCount();
                    return Integer.compare(countB,countA);
                }).collect(Collectors.toList());

        userAndProductDto.setUsers(userSoldDtoList);
        userAndProductDto.setUsersCount(userSoldDtoList.size());
        return userAndProductDto;
    }

    @Override
    public void printGetUserAndProduct() {
       String json = gson.toJson(getUsersAndProductsDto());
        System.out.println(json);
    }
}
