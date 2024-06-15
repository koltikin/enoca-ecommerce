package com.enoca.repository;

import com.enoca.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> getAllByIsDeletedOrderByFirstNameAsc(boolean isDeleted);

    Optional<Customer> findByIdAndIsDeleted(Long customerId, boolean isDeleted);

    boolean existsByEmailAndIsDeleted(String email, boolean isDeleted);
}
