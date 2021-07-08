package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    // JPA는 EntityManager로 모든 것을 동작함. 스프링부트가 EntityManager를 만들어주기 때문에 생성자로 주입만 해주면 됨
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        /**
         *  inline Variable 단축키 : ctrl + alt + n
         */
//        List<Member> result = em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//        return result;
        /**
         * ... from Member (as) m. as가 생략되어있음. `select m`은 *대신 엔티티를 의미하는 m을 쓴거라고 보면 됨
         * 아래에 쿼리문 같은 것을 jpql 이라고함
         */
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
