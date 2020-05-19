package chapter05.refactoring;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReservationTest {

    private Reservation amountDiscountReservation;
    private Reservation percentDiscountReservation;
    private Reservation noneDiscountReservation;

    @Before
    public void 예매하기초기화() {

        amountDiscountReservation = new ReservationAgency().reserve(
                new DiscountableMovie(
                        Money.wons(10000),
                        new AmountDiscountPolicy(
                                Stream.of(new SequenceDiscountCondition(2), new PeriodDiscountCondition(
                                        LocalDateTime.of(2020, 5, 15, 13, 0),
                                        LocalDateTime.of(2020, 5, 15, 15, 30)
                                )).collect(Collectors.toSet()),
                                Money.ZERO
                        ),
                        new Screening(
                                2,
                                LocalDateTime.of(2020, 5, 15, 14, 0)
                        )
                ),
                new Customer("진강민", "testid-1001"),
                2
        );

        percentDiscountReservation = new ReservationAgency().reserve(
                new DiscountableMovie(
                        Money.wons(10000),
                        new PercentDiscountPolicy(
                                Stream.of(new SequenceDiscountCondition(2), new PeriodDiscountCondition(
                                        LocalDateTime.of(2020, 5, 15, 13, 0),
                                        LocalDateTime.of(2020, 5, 15, 15, 30)
                                )).collect(Collectors.toSet()),
                                0.1
                        ),
                        new Screening(
                                2,
                                LocalDateTime.of(2020, 5, 15, 14, 0)
                        )
                ),
                new Customer("진강민", "testid-1001"),
                2
        );

        noneDiscountReservation = new ReservationAgency().reserve(
                new NoneDiscountableMovie(
                        Money.wons(10000),
                        new Screening(
                                2,
                                LocalDateTime.of(2020, 5, 15, 14, 0)
                        )
                ),
                new Customer("진강민", "testid-1001"),
                2
        );
    }

    @Test
    public void 비율할인된_고객의_id와_이름을_얻으면_testid1002와_진강민이다() {
        assertEquals("testid-1002" + "진강민",
                percentDiscountReservation.getCustomer().getId() +
                        percentDiscountReservation.getCustomer().getName());
    }

    @Test
    public void 비율할인된_상영정보의_순서를_얻으면_2이다() {
        assertEquals(2, percentDiscountReservation.getScreening().getSequence());
    }

    @Test
    public void 비율할인된_예매정보의_요금을_얻으면_18000원이다() {
        assertTrue(Money.wons(9000).isEqual(percentDiscountReservation.getFee()));
    }

    @Test
    public void 비율할인된_예매정보의_관객수를_얻으면_2이다() {
        assertEquals(2, percentDiscountReservation.getAudienceCount());
    }

    @Test
    public void 할인되지않은_예매정보의_요금을_얻으면_10000원이다() {
        assertTrue(Money.wons(18000).isEqual(noneDiscountReservation.getFee()));
    }

    // 이전에 TDD를 하면서 잘못 이름지은 메서드 naming
    @Test
    public void 금액할인고객정보테스트() {
        assertEquals("testid-1001", amountDiscountReservation.getCustomer().getId());
        assertEquals("진강민", amountDiscountReservation.getCustomer().getName());
    }

    @Test
    public void 금액할인상영정보테스트() {
        assertEquals(2, amountDiscountReservation.getScreening().getSequence());
    }

    @Test
    public void 금액할인요금테스트() {
        assertTrue(Money.wons(18000).isEqual(amountDiscountReservation.getFee()));
    }

    @Test
    public void 금액할인예매인원수테스트() {
        assertEquals(2, amountDiscountReservation.getAudienceCount());
    }

}