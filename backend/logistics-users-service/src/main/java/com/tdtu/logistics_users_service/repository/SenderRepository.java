package com.tdtu.logistics_users_service.repository;

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

    // List Spring Data REST have been exported: Receiver Entity

    @RestResource(path = "by-customer", rel = "by-customer")
    Page<SenderDetailProjection> searchAllByCustomer_Id(String customerId, Pageable pageable);

    @RestResource(path = "by-id", rel = "by-id")
    SenderDetailProjection searchById(String id);

    @Transactional
    @Modifying
    @Query("UPDATE Sender s SET s.province = :province, s.district = :district, s.ward = :ward, s.street = :street, s.postalCode = :postalCode WHERE s.id = :id")
    @RestResource(path = "update-sender-address", rel = "update-address")
    int updateAddressById(String id, String province, String district, String ward, String street, String postalCode);
}
