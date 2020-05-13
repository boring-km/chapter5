package chapter05.refactoring;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Movie {
    private final Money fee;
    private final Set<DiscountCondition> discountConditions;

    private final MovieType movieType;
    private final Money discountAmount;
    private final double discountPercent;

    public Money getFee() {
        return fee;
    }

    public Set<DiscountCondition> getDiscountConditions() {
        return discountConditions;
    }

    public MovieType getMovieType() {
        return movieType;
    }

    public Money getDiscountAmount() {
        return discountAmount;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public Movie(Money fee, Set<DiscountCondition> discountConditions, MovieType movieType, Money discountAmount, double discountPercent) {
        this.fee = fee;
        this.discountConditions = discountConditions;
        this.movieType = movieType;
        this.discountAmount = discountAmount;
        this.discountPercent = discountPercent;
    }
}

enum MovieType {
    AMOUNT_DISCOUNT,
    PERCENT_DISCOUNT,
    NONE_DISCOUNT
}
