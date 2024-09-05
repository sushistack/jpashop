package com.sushistack.jpashop.service

import com.sushistack.jpashop.domain.*
import com.sushistack.jpashop.repository.ItemRepository
import com.sushistack.jpashop.repository.MemberRepository
import com.sushistack.jpashop.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository
) {

    /**
     * 주문
     */
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long? {
        //엔티티 조회

        val member = memberRepository.findById(memberId).orElse(null)
        val item = itemRepository.findById(itemId).orElse(null)

        //배송정보 생성
        val delivery = Delivery()
        delivery.address = member.address
        delivery.status = DeliveryStatus.READY

        //주문상품 생성
        val orderItem = OrderItem.createOrderItem(item, item.price, count)

        //주문 생성
        val order = Order.createOrder(member, delivery, orderItem)

        //주문 저장
        orderRepository.save(order)

        return order.id
    }

    /**
     * 주문 취소
     */
    @Transactional
    fun cancelOrder(orderId: Long) {
        //주문 엔티티 조회
        val order = orderRepository.findById(orderId).orElse(null)
        //주문 취소
        order.cancel()
    }

    //검색
//    fun findOrders(orderSearch: OrderSearch): List<Order> {
//        return orderRepository.findAllByString(orderSearch)
//    }
}