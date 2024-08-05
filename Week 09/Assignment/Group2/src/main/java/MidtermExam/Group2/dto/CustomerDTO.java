package MidtermExam.Group2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^\\+62\\d{9,13}$", message = "Phone must be a valid Indonesian number starting with +62")
    private String phoneNumber;

    @NotBlank(message = "Status is required")
    private String status;
}
