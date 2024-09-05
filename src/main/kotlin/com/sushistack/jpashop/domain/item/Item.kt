package com.sushistack.jpashop.domain.item

import com.sushistack.jpashop.domain.Category
import com.sushistack.jpashop.exception.NotEnoughStockException
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    val id: Long = 0

    var name: String = ""
    var price = 0
    var stockQuantity = 0

    @ManyToMany(mappedBy = "items")
    val categories: MutableList<Category> = mutableListOf()

    //==비즈니스 로직==//
    /**
     * stock 증가
     */
    fun addStock(quantity: Int) {
        this.stockQuantity += quantity
    }

    /**
     * stock 감소
     */
    fun removeStock(quantity: Int) {
        val restStock = this.stockQuantity - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }
        this.stockQuantity = restStock
    }
}