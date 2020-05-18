package chapter05.refactoring;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class MovieTest {

    private Movie amountDiscountMovie;
    private Movie percentDiscountMovie;

    @Before
    public void 영화정보초기화() {
        Money fee = Money.wons(10000);

        amountDiscountMovie = new Movie(
                fee,
                new AmountDiscountPolicy(
                        Stream.of(new SequenceDiscountCondition(2), new PeriodDiscountCondition(
                                LocalDateTime.of(2020, 5, 15, 13, 0),
                                LocalDateTime.of(2020, 5, 15, 15, 30)
                        )).collect(Collectors.toSet()),
                        Money.wons(1000)
                )
        );
        percentDiscountMovie = new Movie(
                fee,
                new PercentDiscountPolicy(
                        Stream.of(new SequenceDiscountCondition(2), new PeriodDiscountCondition(
                                LocalDateTime.of(2020, 5, 15, 13, 0),
                                LocalDateTime.of(2020, 5, 15, 15, 30)
                        )).collect(Collectors.toSet()),
                        0.1
                )
        );
    }

    @Test
    public void 금액할인된가격계산() {
        assertTrue(Money.wons(9000).isEqual(
                amountDiscountMovie.calculateFee(new Screening(
                        amountDiscountMovie,
                        2,
                        LocalDateTime.of(2020, 5, 15, 14, 0)
                ), 1)
        ));
    }

    @Test
    public void 비율할인된가격계산() {
        assertTrue(Money.wons(9000).isEqual(
                percentDiscountMovie.calculateFee(new Screening(
                        percentDiscountMovie,
                        2,
                        LocalDateTime.of(2020, 5, 15, 14, 0)
                ), 1)
        ));
    }

    @Test
    public void 정책변경할인계산() {
        amountDiscountMovie.changeDiscountPolicy(new PercentDiscountPolicy(
                Stream.of(new SequenceDiscountCondition(2), new PeriodDiscountCondition(
                        LocalDateTime.of(2020, 5, 15, 13, 0),
                        LocalDateTime.of(2020, 5, 15, 15, 30)
                )).collect(Collectors.toSet()),
                0.2
        ));

        assertTrue(Money.wons(8000).isEqual(
                amountDiscountMovie.calculateFee(new Screening(
                        amountDiscountMovie,
                        2,
                        LocalDateTime.of(2020, 5, 15, 14, 0)
                ), 1)
        ));
    }
}