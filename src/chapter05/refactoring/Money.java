package chapter05.refactoring;

import java.math.BigDecimal;

public class Money {
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

    public Money multiply(double percent) {
        return new Money(this.amount.multiply(
                BigDecimal.valueOf(percent)
        ));
    }

    public boolean isEqual(Money other) {
        return amount.compareTo(other.amount) == 0;
    }
}
