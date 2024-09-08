package com.sushistack.jpashop.config

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HibernateConfig {

    /**
     * Jackson 을 통해 json 으로 내려줄 때, LAZY 로 설정된 proxy 객체 내려줄 때 필요한 설정
     * 기본은 null 로 내려 주는 것
     */
    @Bean
    fun hibernate5Module(): Hibernate5Module {
        val hibernate5Module = Hibernate5Module()
        //강제 지연 로딩 설정
        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true)
        return hibernate5Module
    }
}