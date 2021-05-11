package payroll.model.union;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UnionMember implements Serializable {
    
    private UUID id;
    
    private boolean active;

    private Double fee;

    private List<ServiceTax> serviceTaxes;


    public UnionMember(UUID id, boolean active, Double fee) {
        this.id = id;
        this.active = active;
        this.fee = fee;
        this.serviceTaxes = new ArrayList<ServiceTax>();
    }


    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public Double getFee() {
        return this.fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
    

    public List<ServiceTax> getServiceTaxes() {
        return this.serviceTaxes;
    }

    public void setServiceTaxes(List<ServiceTax> serviceTaxes) {
        this.serviceTaxes = serviceTaxes;
    }


    public List<ServiceTax> getServiceTaxesAfterDate(LocalDate date) {
        Predicate<ServiceTax> dateFilter = tax -> tax.getDate().isAfter(date);
        return this.serviceTaxes.stream().filter(dateFilter).collect(Collectors.toList());
    }

    public Double getServiceTaxesSum() {
        Double sum = 0.0;
        for (ServiceTax s : this.serviceTaxes) {
            sum += s.getValue(); 
        }
        return sum;
    }

    public Double getServiceTaxesSumAfterDate(LocalDate date) {
        Double sum = 0.0;
        for (ServiceTax s : getServiceTaxesAfterDate(date)) {
            sum += s.getValue(); 
        }
        return sum;
    }


    @Override
    public String toString() {
        return "\nUnion ID: " + getId() +
            "\nIs an Active Union Member: " + getActive() +
            "\nUnion Fee: " + getFee() +
            "\nServiceTaxes='" + getServiceTaxStrings() + "'";
    }


    private String getServiceTaxStrings() {
        String str = "";
        for (ServiceTax st : this.serviceTaxes) {
            str += "(" + st.toString() + "), ";
        }
        return str;
    }
}
