package com.sushistack.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.sushistack.jpashop.domain.item.Item
import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long = 0

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null

    var orderPrice = 0 //주문 가격
    var count = 0 //주문 수량

    //==비즈니스 로직==//
    fun cancel() {
        item?.addStock(count)
    }

    //==조회 로직==//
    val totalPrice: Int
        get() = orderPrice * count

    companion object {
        //==생성 메서드==//
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem = OrderItem()
            orderItem.item = item
            orderItem.orderPrice = orderPrice
            orderItem.count = count

            item.removeStock(count)
            return orderItem
        }
    }
}