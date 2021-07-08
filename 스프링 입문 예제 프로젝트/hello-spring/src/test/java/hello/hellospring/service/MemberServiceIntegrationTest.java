package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    /**
     * 테스트할 때는 딱히 뭐 문제될건 없으니 간편하게 필드 인젝션 사용해도 됨.
     * @Transactional 어노테이션 쓰면 테스트하고 DB에 저장안하고 롤백 시켜줌(테스트에서만)
     */


    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    // 테스트 메소드는 한글로 작성해도 상관x
    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("spring");

        Long savedId = memberService.join(member);

        Member foundMember = memberService.findOne(savedId).get();

        assertThat(member.getName()).isEqualTo(foundMember.getName());
    }

    @Test
    public void 중복회원예외() {
        Member member1 = new Member();
        member1.setName("spring");

        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("spring");

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

    }


}