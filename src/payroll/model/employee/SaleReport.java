package payroll.model.employee;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class SaleReport implements Serializable {

    private LocalDate date;

    private Double value;


    public SaleReport(LocalDate date, Double value) {
        this.date = date;
        this.value = value;
    }


    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "value: " + this.value + ", date: " + this.date.toString();
    }

    public static String toString(List<SaleReport> saleReports) {
        String str = "";
        for (SaleReport sr : saleReports) {
            str += "(" + sr.toString() + "), ";
        }
        return str;
    }
}
