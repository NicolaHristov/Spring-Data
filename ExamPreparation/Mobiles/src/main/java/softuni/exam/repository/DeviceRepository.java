package softuni.exam.repository;


import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.DeviceType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.domain.Sort.Order.*;
import static org.springframework.data.domain.Sort.by;

//TODO
public interface DeviceRepository extends JpaRepository<Device,Long> {

//        Optional<Device>findAllByModel(String model);
        Optional<Device>findDeviceByModel(String model);

        Optional<Device>findAllByBrandAndModel(String brand, String model);


 //Export all devices of type SMART_PHONE with a price less than 1000 and storage equal to or more than 128 from the Database
//•	Filter only smartphones whose price is less than 1000 and have storage equal to or greater than 128.
// Order the results ascending by device brand.

//        List<Device> findAllByDeviceTypeAndPriceLessThanAndStorageGreaterThanEqualOrderByBrand(DeviceType deviceType, double price, Integer storage);
@Query("select d from Device d where d.price < 1000 and  d.deviceType = 'SMART_PHONE' and d.storage >= 128" +
        " order by LOWER(d.brand) asc")
List<Device> findAllSmartPhonesCheaperThan1000AndStorageMoreThan128();
        List<Device> findDeviceByPriceLessThanAndDeviceTypeAndStorageGreaterThanEqualOrderByBrandAsc(Double price, DeviceType type, int storage);

}
