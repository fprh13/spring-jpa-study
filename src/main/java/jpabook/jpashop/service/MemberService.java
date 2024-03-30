package jpabook.jpashop.service;

import jpabook.jpashop.entity.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 커멘드 성인지 쿼리성 인지 구분 하자
// 영속성 컨텍스트를 플러쉬 안하고 더티채킹안하는 장점
// 디비에 따라서는 읽기 전용 이니까 너무 부화를 일으키지 말고 디비야 너가 읽어줘 하는 그런 드라이버들도 존재
public class MemberService {

    /**
     * @AllArgsConstructor 모든 생성자를 만든다.
     * @RequiredArgsConstructor final 이 있는 필드만 생성자를 만든다.
     */

    /**
     * @Autowired // 스프링이 스프링 빈에 등록되어있는 Repository를 인젝션해줌 (필드 인젝션)
     *      private MemberRepository memberRepository;
     *  단점: 바꿀수 없다. (private 필드이기 떄문에)
     */


    /**
     * setter 인젝션
     *
     *
     *     private MemberRepository memberRepository;
     * @Autowired
     *     public void setMemberRepository(MemberRepository memberRepository) {
     *         this.memberRepository = memberRepository;
     *     }
     * 스프링이 바로 주입되는게 아닌 세터를 통해서 주입
     *
     * 장점: test할떄 mock 같은거 주입 할 수 있음
     * 단점: 애플리케이션 돌아가는 시점에 바꿀 일이 존재 하지 않는데
     * setter를 주면 좋지 않다.
     */

    /**
     * 생성자 인젝션
     * 장점 : 한번 생성하고 끝나고 set해서 바꿀 수 없다.
     *      test 할 때 임의로 new MemberService(); 에 필요한 요소를 mock 같은 것 을 주입해야한다는 것을 상기 시킬수 있음
     */
    private final MemberRepository memberRepository; // 변경할 일이 없기 때문에 final 을 한다. 생성자가 주입되지 않으면 컴파일 시점에 오류 확인

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    @Transactional // 트랜젝션 안에서 관리되어야지 Lazy로딩과 같은 것도 진행이된다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) { // 동시에 가입하는 경우가 있으니 유니크 조건을 걸어야됨.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재 하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 단건 조회
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
