package com.sushistack.jpashop.repository

import com.sushistack.jpashop.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByName(name: String): List<Member>
}