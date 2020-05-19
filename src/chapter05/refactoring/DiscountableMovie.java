package chapter05.refactoring;

public class DiscountableMovie implements Movie {
    private final Money fee;
    private DiscountPolicy discountPolicy;
    private final Screening screening;

    public DiscountableMovie(Money fee, DiscountPolicy discountPolicy, Screening screening) {
        this.fee = fee;
        this.discountPolicy = discountPolicy;
        this.screening = screening;
    }

    @Override
    public Reservation makeReservation(Customer customer, int audienceCount) {
        Money totalFee = getTotalFee(audienceCount);
        return createReservation(customer, screening, totalFee, audienceCount);
    }

    private Money getTotalFee(int audienceCount) {
        return calculateFee(screening, audienceCount);
    }

    // 정보 전문가 - 예매가격 계산에 대해 필요한 정보를 알고 있는 객체에게 책임을 할당 [책임할당 2]
    public Money calculateFee(Screening screening, int audienceCount) {
        return discountPolicy.calculateFee(screening, audienceCount, fee);
    }

    // Creator - 예매정보에 대해 가장 잘 알고 있는 객체에서 Reservation 객체를 생성한다.
    private Reservation createReservation(Customer customer, Screening screening, Money fee, int audienceCount) {
        return new Reservation(customer, screening, fee, audienceCount);
    }

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {   // 할인 정책을 분리하여 영화의 할인 정책이 유연하게 바뀌도록 설계가 바뀌었다.
        this.discountPolicy = discountPolicy;
    }
}
