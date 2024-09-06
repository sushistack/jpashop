package com.sushistack.jpashop.domain

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import java.time.LocalDateTime

@Entity
class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    val id: Long? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableList<OrderItem> = mutableListOf()

    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery? = null

    var orderDate: LocalDateTime = LocalDateTime.now() //주문시간

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.NONE //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    fun changeMember(member: Member) {
        this.member = member
        member.orders.add(this)
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun changeDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    fun cancel() {
        check(delivery?.status !== DeliveryStatus.COMP) { "이미 배송완료된 상품은 취소가 불가능합니다." }

        this.status = OrderStatus.CANCEL
        for (orderItem in orderItems) {
            orderItem.cancel()
        }
    }

    //==조회 로직==//
    val totalPrice: Int
        /**
         * 전체 주문 가격 조회
         */
        get() {
            var totalPrice = 0
            for (orderItem in orderItems) {
                totalPrice += orderItem.totalPrice
            }
            return totalPrice
        }

    companion object {
        //==생성 메서드==//
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
            val order = Order()
            order.changeMember(member)
            order.changeDelivery(delivery)
            for (orderItem in orderItems) {
                order.addOrderItem(orderItem)
            }
            order.status = OrderStatus.ORDER
            order.orderDate = LocalDateTime.now()
            return order
        }
    }
}