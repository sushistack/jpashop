package com.sushistack.jpashop.controller

import com.sushistack.jpashop.controller.model.MemberForm
import com.sushistack.jpashop.domain.Address
import com.sushistack.jpashop.domain.Member
import com.sushistack.jpashop.service.MemberService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MemberController(private val memberService: MemberService) {

    @GetMapping("/members/new")
    fun createForm(model: Model): String {
        model.addAttribute("memberForm", MemberForm())
        return "members/createMemberForm"
    }

    @PostMapping("/members/new")
    fun create(@Valid form: MemberForm, result: BindingResult): String {
        if (result.hasErrors()) {
            return "members/createMemberForm"
        }

        val member = Member()
        member.name = form.name
        member.address = Address(form.city, form.street, form.zipcode)

        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String {
        val members: List<Member> = memberService.findMembers()
        model.addAttribute("members", members)
        return "members/memberList"
    }
}
