package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    //값이 변하지 않음 (final)
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 수정자 주입(setter 주입)을 위한 final 제거
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;

    // 필드 주입 - DI 프레임워크가 없으면 아무것도 할 수 없음
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    // 생성자가 하나만 있다면 @Autowired가 생략된 것과 같음 (자동으로 의존관계가 주입됨)
    // 생성자는 spring life cycle에서 자동으로 등록됨
    // -> 생성자 먼저 등록되기 때문에 먼저 출력됨
//    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 잘 사용하지 않음
    // 스프링 컨테이너가 관리하는 스프링 빈이어야 동작함(의존관계 자동 주입)
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
