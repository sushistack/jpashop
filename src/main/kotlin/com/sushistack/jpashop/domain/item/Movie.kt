package com.sushistack.jpashop.domain.item

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("A")
class Movie : Item() {
    private val director: String = ""
    private val actor: String = ""
}