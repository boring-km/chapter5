package chapter05.refactoring;

import java.util.Set;

public abstract class Movie { // Protected Variation
    private final Money fee;
    private final Set<DiscountCondition> discountConditions;

    public Movie(Money fee, Set<DiscountCondition> discountConditions) {
        this.fee = fee;
        this.discountConditions = discountConditions;
    }

    public Money calculateDiscountableFee(Screening screening, int audienceCount) {
        boolean discountable = checkCondition(screening);
        return calculateFee(fee, discountable, audienceCount);
    }

    // 영화 할인 여부를 판단하기에 필요한 정보를 갖고 있는 DiscountCondition에게 책임을 할당 [책임할당 3]
    private boolean checkCondition(Screening screening) {
        return discountConditions.stream().anyMatch(condition -> condition.checkCondition(screening));
    }

    private Money calculateFee(Money fee, boolean discountable, int audienceCount) {
        if (discountable) {
            return calculate(fee).times(audienceCount);
        }
        return fee.times(audienceCount);
    }

    protected abstract Money calculate(Money fee);
}
// 다형성(Polymorphism)
class AmountDiscountMovie extends Movie {

    private final Money discountAmount;

    public AmountDiscountMovie(Money fee, Set<DiscountCondition> discountConditions, Money discountAmount) {
        super(fee, discountConditions);
        this.discountAmount = discountAmount;
    }

    @Override
    public Money calculate(Money fee) {
        return fee.minus(discountAmount);
    }
}
// 다형성(Polymorphism)
class PercentDiscountMovie extends Movie {

    private final double discountPercent;

    public PercentDiscountMovie(Money fee, Set<DiscountCondition> discountConditions, double discountPercent) {
        super(fee, discountConditions);
        this.discountPercent = discountPercent;
    }

    @Override
    public Money calculate(Money fee) {
        return fee.minus(fee.times(discountPercent));
    }
}
