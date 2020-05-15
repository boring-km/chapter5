package chapter05.refactoring;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ScreeningTest {

    private Screening screening;
    private int sequence;
    private LocalDateTime whenScreened;

    @Before
    public void 상영정보초기화() {
        Movie movie = new AmountDiscountMovie(
                Money.wons(10000),
                Stream.of(new SequenceDiscountCondition(2), new PeriodDiscountCondition(
                        LocalDateTime.of(2020, 5, 15, 13, 0),
                        LocalDateTime.of(2020, 5, 15, 15, 30)
                )).collect(Collectors.toSet()),
                Money.wons(1000)
        );
        sequence = 2;
        whenScreened = LocalDateTime.of(2020, 5, 15, 17, 50);

        screening = new Screening(movie, sequence, whenScreened);
    }

    @Test
    public void 예매정보생성하기() {
        Customer customer = new Customer("진강민", "testid-1001");
        int audienceCount = 2;
        assertNotNull(screening.makeReservation(customer, audienceCount));
    }

    @Test
    public void 상영순서확인하기() {
        assertEquals(sequence, screening.getSequence());
    }

    @Test
    public void 상영시간확인하기() {
        assertEquals(whenScreened, screening.getWhenScreened());
    }
}