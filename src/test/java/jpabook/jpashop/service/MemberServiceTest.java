package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // test에서 transaction은 디비에 insert를 하지 않는다. flush 자체를 하지 않는다.
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
//    @Rollback(value = false)
    void 회원가입() throws Exception {
        //given
        Member member = new Member("kim");

        //when
        Long saveId = memberService.join(member); // 영속성 컨택스트 등록

        //then
//        em.flush(); // 모두 디비에 반영하기 때문에 db 쿼리가 나감.
        assertEquals(member, memberRepository.findOne(saveId));
        System.out.println(member.getName());
    }

    @Test
    void 중복_회원_예외() throws Exception {
        //given
        Member kim1 = new Member("kim");
        Member kim2 = new Member("kim");

        //when
        memberService.join(kim1);
//        memberService.join(kim2); // 예외 발생해야한다.

//        then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(kim2);
        }); // 에러가 발생 해야한다.
    }
}