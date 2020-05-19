package chapter05.refactoring;

public class NoneDiscountableMovie implements Movie {

    private final Money fee;
    private final Screening screening;

    public NoneDiscountableMovie(Money fee, Screening screening) {
        this.fee = fee;
        this.screening = screening;
    }

    @Override
    public Reservation makeReservation(Customer customer, int audienceCount) {
        Money totalFee = getTotalFee(fee, audienceCount);
        return createReservation(customer, screening, totalFee, audienceCount);
    }

    private Money getTotalFee(Money fee, int audienceCount) {
        return fee.multiply(audienceCount);
    }

    // Creator - 예매정보에 대해 가장 잘 알고 있는 Screening 객체에서 Reservation 객체를 생성한다.
    private Reservation createReservation(Customer customer, Screening screening, Money fee, int audienceCount) {
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
