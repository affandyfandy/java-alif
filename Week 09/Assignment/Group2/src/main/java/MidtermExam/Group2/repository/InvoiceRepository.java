package MidtermExam.Group2.repository;

import MidtermExam.Group2.entity.Invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID>, JpaSpecificationExecutor<Invoice> {
    /**
     * Find all invoices with pagination
     * 
     * @param pageable pagination information
     * @return page of invoices
     */
    @Query("SELECT i FROM Invoice i JOIN FETCH i.customer JOIN FETCH i.invoiceProducts")
    Page<Invoice> findAll(Pageable pageable);

    /**
     * Find invoice by id
     * 
     * @param id invoice id
     * @return invoice
     */
    @Query("SELECT i FROM Invoice i JOIN FETCH i.customer JOIN FETCH i.invoiceProducts WHERE i.id = :id")
    Invoice findInvoiceById(UUID id);

    /**
     * Find invoices by customer id and date
     * 
     * @param customerId customer id
     * @param month      month
     * @param year       year
     * @return
     */
    @Query("SELECT i FROM Invoice i WHERE (:customerId IS NULL OR i.customer.id = :customerId) AND (:month IS NULL OR MONTH(i.invoiceDate) = :month) AND (:year IS NULL OR YEAR(i.invoiceDate) = :year)")
    List<Invoice> findByCustomerAndDate(@Param("customerId") UUID customerId,
            @Param("month") Integer month,
            @Param("year") Integer year);

    List<Invoice> findByCustomerId(UUID customerId);

    @Query("SELECT SUM(i.invoiceAmount) FROM Invoice i WHERE i.invoiceDate BETWEEN :startDateTime AND :endDateTime")
    BigDecimal calculateTotalRevenueByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
