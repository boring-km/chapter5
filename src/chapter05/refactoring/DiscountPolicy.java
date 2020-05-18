package chapter05.refactoring;

import java.util.Set;

public interface DiscountPolicy {       // 상속 보단 합성
    Money calculateFee(Screening screening, int audienceCount, Money fee);
}

abstract class DefaultDiscountPolicy implements DiscountPolicy {

    private final Set<DiscountCondition> discountConditions;

    protected DefaultDiscountPolicy(Set<DiscountCondition> conditions) {
        this.discountConditions = conditions;
    }

    @Override
    public Money calculateFee(Screening screening, int audienceCount, Money fee) {
        boolean discountable = checkCondition(screening);
        return getCalculatedFee(fee, discountable, audienceCount);
    }

    // 영화 할인 여부를 판단하기에 필요한 정보를 갖고 있는 DiscountCondition에게 책임을 할당 [책임할당 3]
    private boolean checkCondition(Screening screening) {
        return discountConditions.stream().anyMatch(condition -> condition.checkCondition(screening));
    }

    private Money getCalculatedFee(Money fee, boolean discountable, int audienceCount) {
        if (discountable) {
            return discount(fee).multiply(audienceCount);
        }
        return fee.multiply(audienceCount);
    }

    protected abstract Money discount(Money fee);

}

class AmountDiscountPolicy extends DefaultDiscountPolicy {

    private final Money discountAmount;

    protected AmountDiscountPolicy(Set<DiscountCondition> conditions, Money discountAmount) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    public Money discount(Money fee) {
        return fee.minus(discountAmount);
    }
}

class PercentDiscountPolicy extends DefaultDiscountPolicy {

    private final double discountPercent;

    protected PercentDiscountPolicy(Set<DiscountCondition> conditions, double discountPercent) {
        super(conditions);
        this.discountPercent = discountPercent;
    }

    @Override
    public Money discount(Money fee) {
        return fee.minus(fee.multiply(discountPercent));
    }
}