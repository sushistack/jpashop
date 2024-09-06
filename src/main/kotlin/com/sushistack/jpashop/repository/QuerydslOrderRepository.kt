package com.sushistack.jpashop.repository

import com.sushistack.jpashop.domain.Order
import com.sushistack.jpashop.domain.OrderStatus
import org.springframework.data.domain.Pageable

interface QuerydslOrderRepository {
    fun findAllByMemberNameAndStatus(memberName: String? = null, status: OrderStatus? = null): List<Order>
    fun findAllWithItem(): List<Order>
    fun findAllWithMemberDelivery(): List<Order>
    fun findAllWithMemberDelivery(pageable: Pageable): List<Order>
}