package com.sushistack.jpashop.service

import com.sushistack.jpashop.domain.Member
import com.sushistack.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(private val memberRepository: MemberRepository) {

    /**
     * 회원 가입
     */
    fun join(member: Member): Long = member.let {
        validateDuplicateMember(it) //중복 회원 검증
        memberRepository.save(it).id!!
    }

    private fun validateDuplicateMember(member: Member) {
        val findMembers: List<Member> = memberRepository.findByName(member.name)
        check(findMembers.isEmpty()) { "이미 존재하는 회원입니다." }
    }

    //회원 전체 조회
    fun findMembers(): List<Member> = memberRepository.findAll()

    fun findOne(memberId: Long): Member = memberRepository.findById(memberId).get()

    /**
     * 회원 수정
     */
    @Transactional
    fun update(id: Long, name: String) {
        val member = memberRepository.findById(id).orElse(null)
        member?.name = name
    }
}