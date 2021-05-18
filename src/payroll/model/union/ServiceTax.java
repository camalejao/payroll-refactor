package payroll.model.union;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ServiceTax implements Serializable {
    
    private LocalDate date;
    
    private Double value;


    public ServiceTax(LocalDate date, Double value) {
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

    public static String toString(List<ServiceTax> serviceTaxes) {
        String str = "";
        for (ServiceTax st : serviceTaxes) {
            str += "(" + st.toString() + "), ";
        }
        return str;
    }
}
