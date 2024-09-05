package com.sushistack.jpashop.controller.model

import jakarta.validation.constraints.NotEmpty

data class MemberForm(
    @NotEmpty(message = "회원 이름은 필수 입니다")
    val name: String = "",
    val city: String = "",
    val street: String = "",
    val zipcode: String = ""
)