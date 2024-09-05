package com.sushistack.jpashop.domain.item

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("B")
class Book : Item() {
    var author: String = ""
    var isbn: String = ""
}