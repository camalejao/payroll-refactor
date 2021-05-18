package payroll.model.employee;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Timecard implements Serializable {

    private LocalDate date;

    private LocalTime timeIn;

    private LocalTime timeOut;


    public Timecard(LocalDate date, LocalTime timeIn, LocalTime timeOut) {
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }


    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public LocalTime getTimeIn() {
        return this.timeIn;
    }


    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }


    public LocalTime getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }


    @Override
    public String toString() {
        return "date: " + this.date.toString() + ", time: " + this.timeIn + "-" + this.timeOut;
    }

    public static String toString(List<Timecard> timecards) {
        String str = "";
        for (Timecard tc : timecards) {
            str += "(" + tc.toString() + ") ";
        }
        return str;
    }
}
