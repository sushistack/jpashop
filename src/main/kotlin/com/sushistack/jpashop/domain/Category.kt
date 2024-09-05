package com.sushistack.jpashop.domain

import com.sushistack.jpashop.domain.item.Item
import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    val id: Long? = null

    val name: String? = null

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: MutableList<Item> = mutableListOf()

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category? = null

    @OneToMany(mappedBy = "parent")
    val child: MutableList<Category> = ArrayList()

    //==연관관계 메서드==//
    fun addChildCategory(child: Category) {
        this.child.add(child)
        child.parent = this
    }
}