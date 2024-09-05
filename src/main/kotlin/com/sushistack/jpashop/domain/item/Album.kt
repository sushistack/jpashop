package com.sushistack.jpashop.domain.item

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("A")
class Album : Item() {
    private val artist: String = ""
    private val etc: String = ""
}