package com.enoca.repository;

import com.enoca.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByIdAndIsDeleted(long id, boolean isDeleted);

    Cart findByCustomerIdAndIsDeleted(Long customerId, boolean b);
}
