package com.sushistack.jpashop.api

import com.sushistack.jpashop.domain.Address
import com.sushistack.jpashop.domain.Order
import com.sushistack.jpashop.domain.OrderStatus
import com.sushistack.jpashop.repository.OrderRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

/**
 *
 * xToOne(ManyToOne, OneToOne) 관계 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 *
 */
@RestController
class OrderSimpleApiController(private val orderRepository: OrderRepository) {

    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리
     * - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order> =
        orderRepository.findAllByMemberNameAndStatus().map {
            it.member?.name
            it.delivery?.address

            it
        }

    /**
     * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     */
    @GetMapping("/api/v2/simple-orders")
    fun ordersV2() = orderRepository.findAll().map { o: Order -> SimpleOrderDto(o) }

    /**
     * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * 참고: fetch join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
     */
    @GetMapping("/api/v3/simple-orders")
    fun ordersV3() = orderRepository.findAllWithMemberDelivery().map { o: Order -> SimpleOrderDto(o) }

    /*
    @GetMapping("/api/v4/simple-orders")
    fun ordersV4(): List<OrderSimpleQueryDto> {
        return orderSimpleQueryRepository.findOrderDtos()
    }
    */

    data class SimpleOrderDto(
        private val orderId: Long = 0,
        private val name: String = "",
        private val orderDate: LocalDateTime = LocalDateTime.now(),
        private val orderStatus: OrderStatus = OrderStatus.NONE,
        private val address: Address = Address("", "", "")
    ) {
        constructor(order: Order): this(order.id!!, order.member?.name ?: "", order.orderDate, order.status, order.delivery?.address!!)
    }
}
