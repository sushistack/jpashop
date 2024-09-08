package com.sushistack.jpashop.domain.item

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("M")
class Movie : Item() {
    private val director: String = ""
    private val actor: String = ""
}