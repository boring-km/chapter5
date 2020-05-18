package chapter05.refactoring;

public class Movie {
    private final Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(Money fee, DiscountPolicy discountPolicy) {
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money calculateFee(Screening screening, int audienceCount) {
        return discountPolicy.calculateFee(screening, audienceCount, fee);
    }

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {   // 할인 정책을 분리하여 영화의 할인 정책이 유연하게 바뀌도록 설계가 바뀌었다.
        this.discountPolicy = discountPolicy;
    }
}