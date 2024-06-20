package com.enoca.repository;

import com.enoca.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByIdAndIsDeleted(long id, boolean isDeleted);

    Optional<Cart> findByCustomerIdAndIsDeleted(Long customerId, boolean isDeleted);

    List<Cart> findAllByIsDeleted(boolean isDeleted);
}
