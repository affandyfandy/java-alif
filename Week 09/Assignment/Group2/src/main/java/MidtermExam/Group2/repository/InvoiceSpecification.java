package MidtermExam.Group2.repository;

import MidtermExam.Group2.criteria.InvoiceSearchCriteria;
import MidtermExam.Group2.entity.Invoice;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class InvoiceSpecification implements Specification<Invoice> {

    private final InvoiceSearchCriteria criteria;

    public InvoiceSpecification(InvoiceSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (criteria.getCustomerName() != null && !criteria.getCustomerName().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("customer").get("name"), "%" + criteria.getCustomerName() + "%"));
        }

        if (criteria.getCustomerId() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("customer").get("id"), criteria.getCustomerId()));
        }

        if (criteria.getInvoiceDate() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("invoiceDate"), criteria.getInvoiceDate()));
        }

        if (criteria.getMonth() != null && !criteria.getMonth().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.function("MONTH", Integer.class, root.get("invoiceDate")), Integer.parseInt(criteria.getMonth())));
        }

        if (criteria.getSortBy() != null && !criteria.getSortBy().isEmpty()) {
            if (criteria.isSortAsc()) {
                query.orderBy(criteriaBuilder.asc(root.get(criteria.getSortBy())));
            } else {
                query.orderBy(criteriaBuilder.desc(root.get(criteria.getSortBy())));
            }
        }

        return predicate;
    }
}