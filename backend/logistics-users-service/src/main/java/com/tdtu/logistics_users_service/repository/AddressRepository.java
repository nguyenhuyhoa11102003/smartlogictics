package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "address", path = "address")
public interface AddressRepository extends JpaRepository<Address, String> {

    Optional<Address> findById(Long id);

    @Override
    @RestResource(exported = false)
    <S extends Address> S save(S entity);

    @Override
    @RestResource(exported = false)
    void delete(Address entity);
}
