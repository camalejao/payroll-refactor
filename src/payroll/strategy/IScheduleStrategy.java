package payroll.strategy;

import java.time.LocalDate;

import payroll.model.payments.PaymentSchedule;
import payroll.model.payments.Schedule;

public interface IScheduleStrategy {
    
    public String getScheduleString(PaymentSchedule paymentSchedule);

    public int getDividingFactor();

    public boolean checkIfDateIsInSchedule(PaymentSchedule paymentSchedule, LocalDate date, int weekCounter);

    default public String appendScheduleDescription(Schedule schedule) {
        return schedule.getScheduleDescription() + " ";
    }

}
