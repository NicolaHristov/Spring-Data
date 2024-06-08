package bg.softuni.modelmapper.services;

import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.dto.AddressDTO;
import bg.softuni.modelmapper.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(AddressDTO data) {

        ModelMapper mapper = new ModelMapper();

        Address address = mapper.map(data,Address.class);
        //Искам този адрес да бъде съхранен и за това ми трябва репозитори

        return this.addressRepository.save(address);
    }
}
