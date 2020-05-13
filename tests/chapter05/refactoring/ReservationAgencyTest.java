package chapter05.refactoring;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class ReservationAgencyTest {

    @Test
    public void 예매하기() {
        assertNotNull(new ReservationAgency().reserve(
                new Screening(
                        new Movie(
                                Money.wons(10000),
                                new HashSet<>(Collections.singleton(new DiscountCondition(DiscountConditionType.SEQUENCE, 2, DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)))),
                                MovieType.AMOUNT_DISCOUNT,
                                Money.wons(1000),
                                0.1
                        ),
                        2,
                        LocalDateTime.from(LocalTime.of(11,0))
                ), new Customer("진강민", "testid-1001"), 2
        ));
    }
}
