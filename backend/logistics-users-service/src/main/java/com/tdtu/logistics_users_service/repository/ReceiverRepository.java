package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.entity.Receiver;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "receiver", path = "receiver")
public interface ReceiverRepository extends PagingAndSortingRepository<Receiver, String> {

    List<Receiver> findReceiverByCustomer_Id(String customerId);

    Optional<Receiver> findById(String id);

    @RestResource(exported = false)
    <S extends Receiver> S save(S entity);

    @RestResource(exported = false)
    boolean existsById(String id);

    @RestResource(exported = false)
    void delete(Receiver entity);

}
