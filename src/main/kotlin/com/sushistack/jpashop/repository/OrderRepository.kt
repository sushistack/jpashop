package com.sushistack.jpashop.repository

import com.sushistack.jpashop.domain.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>