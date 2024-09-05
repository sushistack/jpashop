package com.sushistack.config

import com.sushistack.jpashop.domain.*
import com.sushistack.jpashop.domain.item.Book
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DataInitializationConfig(private val initService: InitService) {

    @PostConstruct
    fun init() {
        initService.dbInit1()
        initService.dbInit2()
    }

    @Component
    @Transactional
    class InitService(private val em: EntityManager) {

        fun dbInit1() {
            println("Init1" + this.javaClass)
            val member = createMember("userA", "서울", "1", "1111").also { em.persist(it) }
            val book1 = createBook("JPA1 BOOK", 10000, 100).also { em.persist(it) }
            val book2 = createBook("JPA2 BOOK", 20000, 100).also { em.persist(it) }

            val orderItem1: OrderItem = OrderItem.createOrderItem(book1, 10000, 1)
            val orderItem2: OrderItem = OrderItem.createOrderItem(book2, 20000, 2)

            val delivery: Delivery = createDelivery(member)
            Order.createOrder(member, delivery, orderItem1, orderItem2).let { em.persist(it) }
        }

        fun dbInit2() {
            val member = createMember("userB", "진주", "2", "2222").also { em.persist(it) }
            val book1 = createBook("SPRING1 BOOK", 20000, 200).also { em.persist(it) }
            val book2 = createBook("SPRING2 BOOK", 40000, 300).also { em.persist(it) }

            val orderItem1: OrderItem = OrderItem.createOrderItem(book1, 20000, 3)
            val orderItem2: OrderItem = OrderItem.createOrderItem(book2, 40000, 4)

            val delivery: Delivery = createDelivery(member)
            Order.createOrder(member, delivery, orderItem1, orderItem2).let { em.persist(it) }
        }

        private fun createMember(name: String, city: String, street: String, zipcode: String): Member = Member().also {
                it.name = name
                it.address = Address(city = city, street = street, zipcode = zipcode)
            }

        private fun createBook(name: String, price: Int, stockQuantity: Int): Book = Book().also {
            it.name = name
            it.price = price
            it.stockQuantity = stockQuantity
        }

        private fun createDelivery(member: Member): Delivery = Delivery().also {
            it.address = member.address
        }
    }
}