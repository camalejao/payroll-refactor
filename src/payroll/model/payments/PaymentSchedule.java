package payroll.model.payments;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;

import payroll.strategy.IScheduleStrategy;

public class PaymentSchedule implements Serializable {
    
    private Schedule schedule;

    private Integer dayOfMonth;

    private DayOfWeek dayOfWeek;

    private IScheduleStrategy strategy;


    public PaymentSchedule(Schedule schedule, Integer dayOfMonth, DayOfWeek dayOfWeek,
                            IScheduleStrategy strategy) {
        this.schedule = schedule;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.strategy = strategy;
    }


    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }


    public Integer getDayOfMonth() {
        return this.dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }


    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


    @Override
    public String toString() {
        return this.strategy.getScheduleString(this);
    }


    public int getDividingFactor() {
        return this.strategy.getDividingFactor();
    }


    public boolean checkIfDateIsInSchedule(int weekCounter, LocalDate date) {
        return this.strategy.checkIfDateIsInSchedule(this, date, weekCounter);
    }
}
