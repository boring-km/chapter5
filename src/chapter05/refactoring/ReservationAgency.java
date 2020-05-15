package chapter05.refactoring;

public class ReservationAgency {

    // 정보 전문가 - 예매에 대한 정보를 가장 잘 알고 있는 Screening에게 책임을 할당함 [책임할당 1]
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        return screening.makeReservation(customer, audienceCount);
    }
}
