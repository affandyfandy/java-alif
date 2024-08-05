package MidtermExam.Group2.repository;

import MidtermExam.Group2.entity.Product;
import MidtermExam.Group2.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByStatus(Status status, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndStatus(String name, Status status, Pageable pageable);
}
