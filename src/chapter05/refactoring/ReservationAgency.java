package chapter05.refactoring;

public class ReservationAgency {

    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        boolean discountable = checkDiscountable(screening);
        Money fee = calculateFee(screening, discountable, audienceCount);
        return makeReservation(customer, screening, fee, audienceCount);
    }

    private boolean checkDiscountable(Screening screening) {
        return screening.getMovie().getDiscountConditions().stream().anyMatch(condition -> condition.checkCondition(condition, screening));
    }

    private Money calculateFee(Screening screening, boolean discountable, int audienceCount) {
        if (discountable) {
            return screening.getMovie().getFee().minus(calculateDiscountedFee(screening.getMovie())).times(audienceCount);
        }
        return screening.getMovie().getFee().times(audienceCount);
    }

    private Money calculateDiscountedFee(Movie movie) {

        switch (movie.getMovieType()) {
            case AMOUNT_DISCOUNT:
                return movie.getFee().minus(movie.getDiscountAmount());

            case PERCENT_DISCOUNT:
                return movie.getFee().minus(movie.getFee().times(movie.getDiscountPercent()));

            case NONE_DISCOUNT:
                return Money.ZERO;

            default:
                break;
        }
        throw new IllegalArgumentException();
    }

    private Reservation makeReservation(Customer customer, Screening screening, Money fee, int audienceCount) {
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
