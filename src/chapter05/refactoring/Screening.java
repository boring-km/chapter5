package chapter05.refactoring;

import java.time.LocalDateTime;

public class Screening {

    private final Movie movie;
    private final int sequence;
    private final LocalDateTime whenScreened;

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    public int getSequence() {
        return sequence;
    }

    public LocalDateTime getWhenScreened() {
        return whenScreened;
    }

    public Reservation makeReservation(Customer customer, int audienceCount) {
        Money fee = getFee(audienceCount);
        return createReservation(customer, this, fee, audienceCount);
    }

    // 정보 전문가 - 예매가격 계산에 대해 필요한 정보를 알고 있는 Movie에게 책임을 할당 [책임할당 2]
    private Money getFee(int audienceCount) {
        return movie.calculateFee(this, audienceCount);
    }

    // Creator - 예매정보에 대해 가장 잘 알고 있는 Screening 객체에서 Reservation 객체를 생성한다.
    private Reservation createReservation(Customer customer, Screening screening, Money fee, int audienceCount) {
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
