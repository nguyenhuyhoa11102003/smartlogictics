package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    Optional<Customer> findById(String id);

    @RestResource(exported = false)
    <S extends Customer> S save(S entity);

    @RestResource(exported = false)
    void delete(Customer entity);
}
