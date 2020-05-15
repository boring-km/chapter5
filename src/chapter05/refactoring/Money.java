package chapter05.refactoring;

import java.math.BigDecimal;

public class Money {
    public static final Money ZERO = Money.wons(0);
    private final BigDecimal amount; // State Pattern?

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money wons(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public Money minus(Money amount) {
        return new Money(this.amount.subtract(amount.amount));
    }

    public Money times(double percent) {
        return new Money(this.amount.multiply(
                BigDecimal.valueOf(percent)
        ));
    }

    public boolean isLessThan(Money other) {
        return amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterThan(Money other) {
        return amount.compareTo(other.amount) > 0;
    }

    public boolean isEqual(Money other) {
        return amount.compareTo(other.amount) == 0;
    }
}
