package com.enoca.repository;

import com.enoca.entity.OrderCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCodRepository extends JpaRepository<OrderCode,Long> {
}
