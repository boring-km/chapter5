package chapter05.refactoring;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class MovieTest {

    private Movie discountableMovie;
    private Movie noneDiscountableMovie;

    @Before
    public void 영화정보초기화() {
        Money fee = Money.wons(10000);
        Screening screening = new Screening(
                2,
                LocalDateTime.of(2020, 5, 19, 16, 54)
        );

        discountableMovie = new DiscountableMovie(
                fee,
                new AmountDiscountPolicy(
                        Stream.of(new SequenceDiscountCondition(2), new PeriodDiscountCondition(
                                LocalDateTime.of(2020, 5, 19, 16, 0),
                                LocalDateTime.of(2020, 5, 19, 18, 0)
                        )).collect(Collectors.toSet()),
                        Money.wons(1000)
                ),
                screening

        );
        noneDiscountableMovie = new NoneDiscountableMovie(fee, screening);
    }

    @Test
    public void 금액할인된가격계산() {
        assertTrue(Money.wons(9000).isEqual(
                discountableMovie.makeReservation(
                        new Customer("진강민", "testid-02"),
                        1
                ).getFee()));
    }

    @Test
    public void 할인이없는가격계산() {
        assertTrue(Money.wons(9000).isEqual(
                noneDiscountableMovie.makeReservation(new Customer("진강민", "testid-02"), 1).getFee()));
    }

    @Test
    public void 정책변경할인계산() {
        // discountableMovie.changeDiscountPolicy();
    }
}