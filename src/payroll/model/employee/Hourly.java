package payroll.model.employee;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import payroll.model.payments.Paycheck;
import payroll.model.payments.PaymentInfo;
import payroll.model.union.UnionMember;

public class Hourly extends Employee {
    
    private Double hourlyRate;

    private List<Timecard> timecards;


    public Hourly(UUID id, String name, String address, UnionMember unionMember,
                    PaymentInfo paymentInfo, Double hourlyRate) {
        super(id, name, address, unionMember, paymentInfo);
        this.hourlyRate = hourlyRate;
        this.timecards = new ArrayList<Timecard>();
    }


    public Double getHourlyRate() {
        return this.hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }


    public List<Timecard> getTimecards() {
        return this.timecards;
    }

    public void setTimecards(List<Timecard> timecards) {
        this.timecards = timecards;
    }


    @Override
    public String toString() {
        return super.toString() + "\nHourly: {" +
            " hourlyRate='" + getHourlyRate() + "'" +
            ", timecards='" + Timecard.toString(this.timecards) + "'" +
            "}";
    }


    @Override
    Double calcPayment(LocalDate paymentDate) {
        Double payment = 0.0, hours = 0.0, extraHours = 0.0;
        List<Timecard> validTimecards;
        List<Paycheck> paychecks = this.getPaymentInfo().getPaychecks();
        
        Predicate<Timecard> dateFilter;
        if (paychecks != null && !paychecks.isEmpty()) {
            LocalDate lastPaymentDate = paychecks.get(paychecks.size() - 1).getDate();
            dateFilter = timecard -> timecard.getDate().isAfter(lastPaymentDate) && !timecard.getDate().isAfter(paymentDate);
        } else {
            dateFilter = timecard -> !timecard.getDate().isAfter(paymentDate);
        }
        
        validTimecards = this.getTimecards().stream().filter(dateFilter).collect(Collectors.toList());
        
        for (Timecard t : validTimecards) {
            LocalTime timeIn = t.getTimeIn();
            LocalTime timeOut = t.getTimeOut();
            Duration duration = Duration.between(timeIn, timeOut);
            hours = (double) duration.toSeconds() / 3600;

            if (hours > 8.0) {
                extraHours = hours - 8.0;
                payment += 8.0 * this.getHourlyRate();
                payment += extraHours * this.getHourlyRate() * 1.5;
            } else if (hours >= 0.0) {
                payment += hours * this.getHourlyRate();
            }
        }

        return payment;
    }
}
