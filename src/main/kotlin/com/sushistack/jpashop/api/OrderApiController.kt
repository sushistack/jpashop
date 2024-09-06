package com.sushistack.jpashop.api

import com.sushistack.jpashop.domain.Address
import com.sushistack.jpashop.domain.Order
import com.sushistack.jpashop.domain.OrderItem
import com.sushistack.jpashop.domain.OrderStatus
import com.sushistack.jpashop.repository.OrderRepository
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime


/* V1. 엔티티 직접 노출
* - 엔티티가 변하면 API 스펙이 변한다.
* - 트랜잭션 안에서 지연 로딩 필요
* - 양방향 연관관계 문제
*
* V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
* - 트랜잭션 안에서 지연 로딩 필요
* V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
* - 페이징 시에는 N 부분을 포기해야함(대신에 batch fetch size? 옵션 주면 N -> 1 쿼리로 변경 가능)
*
* V4. JPA에서 DTO로 바로 조회, 컬렉션 N 조회 (1 + N Query)
* - 페이징 가능
* V5. JPA에서 DTO로 바로 조회, 컬렉션 1 조회 최적화 버전 (1 + 1 Query)
* - 페이징 가능
* V6. JPA에서 DTO로 바로 조회, 플랫 데이터(1Query) (1 Query)
* - 페이징 불가능...
*
*/
@RestController
class OrderApiController(private val orderRepository: OrderRepository) {


    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리
     * - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    @GetMapping("/api/v1/orders")
    fun ordersV1(): List<Order> =
        orderRepository.findAll().map {
            // force loaded all
            it.member?.name
            it.delivery?.address
            it.orderItems.forEach { orderItem -> orderItem.item?.name }

            it
        }

    @GetMapping("/api/v2/orders")
    fun ordersV2() = orderRepository.findAll().map { o: Order -> OrderDto(o) }

    @GetMapping("/api/v3/orders")
    fun ordersV3() = orderRepository.findAllWithItem().map { o: Order -> OrderDto(o) }

    /**
     * V3.1 엔티티를 조회해서 DTO로 변환 페이징 고려
     * - ToOne 관계만 우선 모두 페치 조인으로 최적화
     * - 컬렉션 관계는 hibernate.default_batch_fetch_size, @BatchSize로 최적화
     */
    @GetMapping("/api/v3.1/orders")
    fun ordersV3Page(pageable: Pageable) = orderRepository.findAllWithMemberDelivery().map { o: Order -> OrderDto(o) }


    /*
    @GetMapping("/api/v4/orders")
    fun ordersV4(): List<OrderQueryDto> {
        return orderQueryRepository.findOrderQueryDtos()
    }

    @GetMapping("/api/v5/orders")
    fun ordersV5(): List<OrderQueryDto> {
        return orderQueryRepository.findAllByDto_optimization()
    }

    @GetMapping("/api/v6/orders")
    fun ordersV6(): List<OrderQueryDto> {
        val flats: List<OrderFlatDto> = orderQueryRepository.findAllByDto_flat()

        return flats.stream()
            .collect(
                Collectors.groupingBy<Any, Any, Any, List<Any>>(
                    Function<Any, Any> { o: Any ->
                        OrderQueryDto(
                            o.getOrderId(),
                            o.getName(),
                            o.getOrderDate(),
                            o.getOrderStatus(),
                            o.getAddress()
                        )
                    },
                    Collectors.mapping<Any, Any, Any, List<Any>>(Function<Any, Any> { o: Any ->
                        OrderItemQueryDto(
                            o.getOrderId(),
                            o.getItemName(),
                            o.getOrderPrice(),
                            o.getCount()
                        )
                    }, Collectors.toList<Any>())
                )
            ).entrySet().stream()
            .map { e ->
                OrderQueryDto(
                    e.getKey().getOrderId(),
                    e.getKey().getName(),
                    e.getKey().getOrderDate(),
                    e.getKey().getOrderStatus(),
                    e.getKey().getAddress(),
                    e.getValue()
                )
            }
            .collect(Collectors.toList<T>())
    }
    */


    data class OrderDto(
        val orderId: Long = 0,
        val name: String = "",
        val orderDate: LocalDateTime = LocalDateTime.now(),
        val orderStatus: OrderStatus = OrderStatus.NONE,
        val address: Address = Address("", ""),
        val orderItems: List<OrderItemDto> = listOf()
    ) {

        constructor(order: Order) : this(order.id!!, order.member?.name ?: "", order.orderDate, order.status, order.delivery?.address!!, order.orderItems.map { OrderItemDto(it) })
    }

    data class OrderItemDto(
        val itemName: String = "",
        val orderPrice: Int = 0,
        val count: Int = 0,
    ) {
        constructor(orderItem: OrderItem) : this(orderItem.item?.name ?: "", orderItem.orderPrice, orderItem.count)
    }
}