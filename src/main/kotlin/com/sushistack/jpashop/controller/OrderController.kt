package com.sushistack.jpashop.controller

import com.sushistack.jpashop.domain.Member
import com.sushistack.jpashop.domain.item.Item
import com.sushistack.jpashop.service.ItemService
import com.sushistack.jpashop.service.MemberService
import com.sushistack.jpashop.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class OrderController(
    private val orderService: OrderService,
    private val memberService: MemberService,
    private val itemService: ItemService
) {


    @GetMapping("/order")
    fun createForm(model: Model): String {
        val members: List<Member> = memberService.findMembers()
        val items: List<Item> = itemService.findItems()

        model.addAttribute("members", members)
        model.addAttribute("items", items)

        return "order/orderForm"
    }

    @PostMapping("/order")
    fun order(
        @RequestParam("memberId") memberId: Long,
        @RequestParam("itemId") itemId: Long,
        @RequestParam("count") count: Int
    ): String {
        orderService.order(memberId, itemId, count)
        return "redirect:/orders"
    }

//    @GetMapping("/orders")
//    fun orderList(@ModelAttribute("orderSearch") orderSearch: OrderSearch?, model: Model): String {
//        val orders: List<Order> = orderService
//        model.addAttribute("orders", orders)
//
//        return "order/orderList"
//    }

    @PostMapping("/orders/{orderId}/cancel")
    fun cancelOrder(@PathVariable("orderId") orderId: Long): String {
        orderService.cancelOrder(orderId)
        return "redirect:/orders"
    }
}
