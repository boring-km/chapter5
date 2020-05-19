package chapter05.refactoring;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;

public class ReservationAgencyTest {

    @Test
    public void 예매에이전시가_영화를_예매하면_결과값이_null이_아니다() {
        assertNotNull(new ReservationAgency().reserve(
                new DiscountableMovie(
                        Money.wons(10000),
                        new AmountDiscountPolicy(
                                Stream.of(new SequenceDiscountCondition(2), new PeriodDiscountCondition(
                                        LocalDateTime.of(2020, 5, 15, 13, 0),
                                        LocalDateTime.of(2020, 5, 15, 15, 30)
                                )).collect(Collectors.toSet()),
                                Money.wons(1000)
                        ),
                        new Screening(
                                2,
                                LocalDateTime.of(2020, 5, 15, 14, 0)
                        )
                ),
                new Customer("진강민", "testid-1001"),
                2
        ));
    }
}