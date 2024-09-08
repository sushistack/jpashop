package com.sushistack.jpashop.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sushistack.jpashop.domain.Order
import com.sushistack.jpashop.domain.OrderStatus
import com.sushistack.jpashop.domain.QDelivery.delivery
import com.sushistack.jpashop.domain.QMember.member
import com.sushistack.jpashop.domain.QOrder.order
import com.sushistack.jpashop.domain.QOrderItem.orderItem
import com.sushistack.jpashop.domain.item.QItem.item
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class QuerydslOrderRepositoryImpl(private val queryFactory: JPAQueryFactory) : QuerydslOrderRepository {
    override fun findAllByMemberNameAndStatus(memberName: String?, status: OrderStatus?): List<Order> =
        queryFactory
            .selectFrom(order)
            .where(order.member.name.eq(memberName).and(order.status.eq(status)))
            .fetch()

    override fun findAllWithItem(): List<Order> =
        queryFactory
            .selectFrom(order)
            .join(order.member, member).fetchJoin()
            .join(order.delivery, delivery).fetchJoin()
            .fetch()


    override fun findAllWithMemberDelivery(): List<Order> =
        queryFactory
            .selectFrom(order)
            .join(order.member, member).fetchJoin()
            .join(order.delivery, delivery).fetchJoin()
            .join(order.orderItems, orderItem).fetchJoin()
            .join(orderItem.item, item).fetchJoin()
            .fetch()

    override fun findAllWithMemberDelivery(pageable: Pageable): List<Order> =
        queryFactory
            .selectFrom(order)
            .join(order.member, member).fetchJoin()
            .join(order.delivery, delivery).fetchJoin()
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
}