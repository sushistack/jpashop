package com.sushistack.jpashop.config

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HibernateConfig {

    @Bean
    fun hibernate5Module(): Hibernate5Module {
        val hibernate5Module = Hibernate5Module()
        //강제 지연 로딩 설정
        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true)
        return hibernate5Module
    }
}