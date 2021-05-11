package payroll.strategy;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import payroll.model.payments.PaymentSchedule;

public class BiweeklyStrategy implements IScheduleStrategy, Serializable {

    @Override
    public String getScheduleString(PaymentSchedule paymentSchedule) {
        return appendScheduleDescription(paymentSchedule.getSchedule()) +
            paymentSchedule.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
    }

    @Override
    public int getDividingFactor() {
        return 2;
    }

    @Override
    public boolean checkIfDateIsInSchedule(PaymentSchedule paymentSchedule, LocalDate date, int weekCounter) {
        return paymentSchedule.getDayOfWeek() == date.getDayOfWeek() && weekCounter % 2 == 0;
    }
    
}
