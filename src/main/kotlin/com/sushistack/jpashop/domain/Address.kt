package com.sushistack.jpashop.domain

import jakarta.persistence.Embeddable

@Embeddable
class Address(
    var city: String? = null,
    var street: String? = null,
    var zipcode: String? = null
)