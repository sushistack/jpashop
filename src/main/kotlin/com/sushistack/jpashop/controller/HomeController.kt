package com.sushistack.jpashop.controller

import com.sushistack.config.Log
import org.slf4j.Logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class HomeController {

    @Log
    private lateinit var log: Logger

    @RequestMapping("/")
    fun home(): String {
        log.info("home controller")
        return "home"
    }
}