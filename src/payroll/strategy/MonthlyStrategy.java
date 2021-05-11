package payroll.strategy;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import payroll.app.util.DateUtils;
import payroll.model.payments.PaymentSchedule;

public class MonthlyStrategy implements IScheduleStrategy {

    private String appendDayOfMonth(Integer dayOfMonth) {
        return dayOfMonth != null ? dayOfMonth.toString() : "$";
    }
    
    @Override
    public String getScheduleString(PaymentSchedule paymentSchedule) {
        return appendScheduleDescription(paymentSchedule.getSchedule()) +
            appendDayOfMonth(paymentSchedule.getDayOfMonth());
    }

    @Override
    public int getDividingFactor() {
        return 1;
    }

    @Override
    public boolean checkIfDateIsInSchedule(PaymentSchedule paymentSchedule, LocalDate date, int weekCounter) {
        Integer dayOfMonth = paymentSchedule.getDayOfMonth();
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate lastWorkingDate = DateUtils.getLastWorkingDateOfMonth(lastDayOfMonth);

        if (dayOfMonth != null) {
            return dayOfMonth == date.getDayOfMonth();
        } else {
            return date.equals(lastWorkingDate);
        }
    }
    
}
