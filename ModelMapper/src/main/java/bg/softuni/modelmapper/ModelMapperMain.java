package bg.softuni.modelmapper;

import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.Employee;
import bg.softuni.modelmapper.entities.dto.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//@Component
public class ModelMapperMain implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Test");

        ModelMapper mapper = new ModelMapper();
//        PropertyMap<Employee,EmployeeDTO> propertyMap = new PropertyMap<>() {
//            @Override
//            protected void configure() {
//                map().setAddressCity(source.getAddress().getCity());
//            }
//        };
//        mapper.addMappings(propertyMap); // Казвай addMapping ние сме предифинирали пред МоделМапера как да се случва това преобразуване
        // Това е същото като -->
        TypeMap<Employee, EmployeeDTO> typeMap = mapper.createTypeMap(Employee.class, EmployeeDTO.class);
        typeMap.addMappings(mapping ->  mapping.map(source -> source.getAddress().getCity(),EmployeeDTO::setAddressCity));
//        typeMap.validate();

        Address address = new Address("Bulgaria","Sofia");
        Employee employee = new Employee("First", BigDecimal.TEN,address);

        EmployeeDTO employeeDTO = mapper.map(employee,EmployeeDTO.class);

        System.out.println(employeeDTO);
    }
}
