package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.entity.Receiver;
import com.tdtu.logistics_users_service.repository.projections.ReceiverDetailProjection;
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
        collectionResourceRel = "receiver",
        path = "receiver",
        excerptProjection = ReceiverDetailProjection.class
)
public interface ReceiverRepository extends PagingAndSortingRepository<Receiver, String> {

    @RestResource(exported = false)
    List<Receiver> findReceiverByCustomer_Id(String customerId);

    @RestResource(exported = false)
    Optional<Receiver> findById(String id);

    @RestResource(exported = false)
    <S extends Receiver> S save(S entity);

    @RestResource(exported = false)
    <S extends Receiver> void delete(S entity);

    // List Spring Data REST have been exported: Receiver Entity

    @RestResource(path = "by-customer", rel = "by-customer")
    Page<Receiver> searchByCustomer_Id(String customerId, Pageable pageable);

    @RestResource(path = "by-id", rel = "by-id")
    List<Receiver> searchById(String id);

    @RestResource(exported = true)
    boolean existsById(String id);

    @Transactional
    @Modifying
    @Query("UPDATE Receiver r SET r.province = :province, r.district = :district, r.ward = :ward, r.street = :street, r.postalCode = :postalCode WHERE r.id = :id")
    @RestResource(path = "update-receiver-address", rel = "update-address")
    int updateAddressById(String id, String province, String district, String ward, String street, String postalCode);

}
