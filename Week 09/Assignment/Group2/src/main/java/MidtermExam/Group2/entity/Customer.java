package MidtermExam.Group2.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Data
@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^\\+62\\d{9,13}$", message = "Phone must be a valid Indonesian number starting with +62")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @PrePersist
    public void prePersist() {
        if (createdTime == null) {
            createdTime = LocalDateTime.now();
        }
        if (updatedTime == null) {
            updatedTime = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedTime = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Invoice> invoice;
}
