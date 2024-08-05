package MidtermExam.Group2.mapper;

import MidtermExam.Group2.dto.CustomerDTO;
import MidtermExam.Group2.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);
}
