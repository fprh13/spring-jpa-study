package jpabook.jpashop.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository{

//    @PersistenceContext //생성자 없을 때 사용 spring 이 EntityManager를 만들어서 여기에 인잭션 하게 해줌
    private final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public Long save(Member member) {
        em.persist(member); // 영속성 컨텍스트에 등록이 된다. 그떄 영속성 컨텍스트는 키 벨류에서 키는 DB의 PK가 된다. 디비에 안직 들어간 상태가 아니여도 id를 넣어주게 됨
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // 객체에 대한 쿼리를 만듬
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
