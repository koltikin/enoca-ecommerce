package com.enoca.repository;

import com.enoca.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OderItemRepository extends JpaRepository<OrderItem, Long> {
}
