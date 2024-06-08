package bg.softuni.modelmapper.services;

import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.dto.AddressDTO;

public interface AddressService {

    Address create(AddressDTO data);
}
