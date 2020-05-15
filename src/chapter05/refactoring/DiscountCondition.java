package chapter05.refactoring;

import java.time.LocalDateTime;

public interface DiscountCondition {     // 다형성(Polymorphism), Protected Variation

    boolean checkCondition(Screening screening);    // Indirection
}

class SequenceDiscountCondition implements DiscountCondition {

    private final int sequence;

    SequenceDiscountCondition(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean checkCondition(Screening screening) {
        return checkSequenceCondition(screening);
    }

    private boolean checkSequenceCondition(Screening screening) {
        return sequence == screening.getSequence();
    }
}

class PeriodDiscountCondition implements DiscountCondition {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public PeriodDiscountCondition(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean checkCondition(Screening screening) {
        return checkPeriodCondition(screening);
    }

    private boolean checkPeriodCondition(Screening screening) {
        return startTime.isBefore(screening.getWhenScreened()) &&
                endTime.isAfter(screening.getWhenScreened());
    }
}