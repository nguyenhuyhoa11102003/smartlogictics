package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.dto.model.SenderDetailDTO;
import com.tdtu.logistics_users_service.entity.Sender;
import com.tdtu.logistics_users_service.repository.projections.SenderDetailProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(
        collectionResourceRel = "sender",
        path = "sender",
        excerptProjection = SenderDetailProjection.class
)
public interface SenderRepository extends PagingAndSortingRepository<Sender, String> {

    @RestResource(exported = false)
    Optional<Sender> findById(String id);

    @RestResource(exported = false)
    List<Sender> findByCustomer_Id(String customerId);

    @RestResource(exported = false)
    <S extends Sender> S save(S entity);

    @RestResource(exported = false)
    <S extends Sender> void delete(S entity);

    // Method to find Sender detail by ID with address concatenation
    @RestResource(exported = false)
    @Query("SELECT new com.tdtu.logistics_users_service.dto.model.SenderDetailDTO(s.id, s.fullName, CONCAT(s.address.province, ', ', s.address.district, ', ', s.address.ward, ', ', s.address.street, ', ', s.address.postalCode)) " +
            "FROM Sender s WHERE s.id = :id")
    Optional<SenderDetailDTO> findSenderDetailById(String id);

    // Method to get a page of Sender details by Customer ID with address concatenation
    @RestResource(exported = false)
    @Query("SELECT new com.tdtu.logistics_users_service.dto.model.SenderDetailDTO(s.id, s.fullName, CONCAT(s.address.province, ', ', s.address.district, ', ', s.address.ward, ', ', s.address.street, ', ', s.address.postalCode)) " +
            "FROM Sender s WHERE s.customer.id = :customerId")
    Page<SenderDetailDTO> searchByCustomerIdDto(String customerId, Pageable pageable);

    // List Spring Data REST have been exported: Sender Entity

    @RestResource(path = "by-customer", rel = "by-customer")
    Page<Sender> searchAllByCustomer_Id(String customerId, Pageable pageable);

    @RestResource(path = "by-id", rel = "by-id")
    Sender searchById(String id);

    @Transactional
    @Modifying
    @Query("UPDATE Sender s SET s.address.province = :province, s.address.district = :district, s.address.ward = :ward, s.address.street = :street, s.address.postalCode = :postalCode WHERE s.id = :id")
    @RestResource(path = "update-sender-address", rel = "update-address")
    int updateAddressById(String id, String province, String district, String ward, String street, String postalCode);
}
