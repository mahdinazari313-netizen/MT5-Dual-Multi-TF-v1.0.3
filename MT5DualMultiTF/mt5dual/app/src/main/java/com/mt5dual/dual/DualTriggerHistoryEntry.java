package com.mt5dual.dual;

import com.mt5dual.core.Direction;
import com.mt5dual.core.Signal;
import com.mt5dual.core.Timeframe;

/**
 * یک رکورد ثابت و تغییرناپذیر از یک DualTrigger که ساخته شده - Snapshot
 * کامل، فقط برای مرور در Forward Test (B-26). کاملاً مستقل از
 * activeTriggers: Repeat، Expire و Dismiss هیچ‌کدام چیزی از تاریخچه حذف
 * نمی‌کنند؛ حذف فقط بر اساس زمان (Retention) در cleanOldHistory انجام
 * می‌شود.
 *
 * referenceSignal/incomingSignal همان دو Signal کامل ترکیب Trigger
 * هستند (نه یک خلاصه متنی) تا داده برای تحلیل/فیلتر بعدی ساختاریافته
 * بماند.
 */
public final class DualTriggerHistoryEntry {
    private final String triggerId;
    private final String symbol;
    private final Timeframe timeframe;
    private final Direction direction;
    private final Signal referenceSignal;
    private final Signal incomingSignal;
    private final long createdAt;

    public DualTriggerHistoryEntry(String triggerId,
                                   String symbol,
                                   Timeframe timeframe,
                                   Direction direction,
                                   Signal referenceSignal,
                                   Signal incomingSignal,
                                   long createdAt) {
        this.triggerId = triggerId;
        this.symbol = symbol;
        this.timeframe = timeframe;
        this.direction = direction;
        this.referenceSignal = referenceSignal;
        this.incomingSignal = incomingSignal;
        this.createdAt = createdAt;
    }

    public String getTriggerId() { return triggerId; }
    public String getSymbol() { return symbol; }
    public Timeframe getTimeframe() { return timeframe; }
    public Direction getDirection() { return direction; }
    public Signal getReferenceSignal() { return referenceSignal; }
    public Signal getIncomingSignal() { return incomingSignal; }
    public long getCreatedAt() { return createdAt; }
}
