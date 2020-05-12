package chapter05.refactoring;

public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        Movie movie = screening.getMovie();

        boolean discountable = false;
        for (DiscountCondition condition : movie.getDiscountConditions()) {
            if (condition.getType() == DiscountConditionType.PERIOD) {
                discountable = screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) &&
                        condition.getStartTime().compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                        condition.getEndTime().compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
            } else {
                discountable = condition.getSequence() == screening.getSequence();
            }
            if (discountable) {
                break;
            }
        }
        Money fee = movie.getFee();
        if (discountable) {
            switch (movie.getMovieType()) {
                case AMOUNT_DISCOUNT:
                    fee = movie.getFee().minus(movie.getDiscountAmount());
                    break;
                case PERCENT_DISCOUNT:
                    fee = movie.getFee().minus(movie.getFee().times(movie.getDiscountPercent()));
                    break;
                case NONE_DISCOUNT:
                    break;
                default:
                    break;
            }
        }
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
