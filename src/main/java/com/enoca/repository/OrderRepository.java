package com.enoca.repository;

import com.enoca.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderByOrderCodeAndIsDeleted(String orderCode,boolean isDeleted);

    List<Order> findOrderByCustomerIdAndIsDeleted(Long customerId, boolean isDeleted);

    List<Order> findAllByIsDeleted(boolean isDeleted);
}
