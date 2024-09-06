package com.sushistack.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long? = null

    @JsonIgnore
    @OneToOne(mappedBy = "changeDelivery", fetch = LAZY)
    var order: Order? = null

    @Embedded
    var address: Address? = null

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus = DeliveryStatus.NONE //READY, COMP
}