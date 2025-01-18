package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.dto.model.CustomerDetailDTO;
import com.tdtu.logistics_users_service.entity.Customer;
import com.tdtu.logistics_users_service.repository.projections.CustomerDetailProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(
        collectionResourceRel = "customer",
        path = "customer",
        excerptProjection = CustomerDetailProjection.class
)
public interface CustomerRepository extends PagingAndSortingRepository<Customer, String> {

    @RestResource(exported = false)
    Optional<Customer> findByEmail(String email);

    @RestResource(exported = false)
    Optional<Customer> findByPhoneNumber(String phoneNumber);

    @RestResource(exported = false)
    Optional<Customer> findById(String id);

    @RestResource(exported = false)
    <S extends Customer> S save(S entity);

    @RestResource(exported = false)
    void delete(Customer entity);

    // Method to find Customer detail by ID with address concatenation
    @RestResource(exported = false)
    @Query("SELECT new com.tdtu.logistics_users_service.dto.model.CustomerDetailDTO(c.id, c.fullName, CONCAT(c.address.province, ', ', c.address.district, ', ', c.address.ward, ', ', c.address.street, ', ', c.address.postalCode)) " +
            "FROM Customer c WHERE c.id = :id")
    Optional<CustomerDetailDTO> findCustomerDetailById(String id);

    // Method to get a page of Customer details with address concatenation
    @RestResource(exported = false)
    @Query("SELECT new com.tdtu.logistics_users_service.dto.model.CustomerDetailDTO(c.id, c.fullName, CONCAT(c.address.province, ', ', c.address.district, ', ', c.address.ward, ', ', c.address.street, ', ', c.address.postalCode)) " +
            "FROM Customer c")
    Page<CustomerDetailDTO> searchAllCustomersWithAddress(Pageable pageable);

    // List Spring Data REST have been exported: Receiver Entity

    @RestResource(path = "by-email", rel = "by-email")
    Customer searchByEmail(String email);

    @RestResource(path = "by-phone-number", rel = "by-phone-number")
    Customer searchByPhoneNumber(String phoneNumber);

    @RestResource(path = "by-id", rel = "by-id")
    Customer searchById(String id);
}
