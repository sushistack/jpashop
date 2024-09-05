package com.sushistack.jpashop.controller.model

data class BookForm(
     var id: Long? = null,
     var name: String = "",
     var price: Int = 0,
     var stockQuantity: Int = 0,
     var author: String = "",
     var isbn: String = "",
)