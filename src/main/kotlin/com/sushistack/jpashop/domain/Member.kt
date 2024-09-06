package com.sushistack.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null

    var name: String = ""

    @Embedded
    var address: Address? = null

    @JsonIgnore
    @OneToMany(mappedBy = "changeMember")
    val orders: MutableList<Order> = mutableListOf()
}