package payroll.model.employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import payroll.model.payments.Paycheck;
import payroll.model.payments.PaymentInfo;
import payroll.model.union.UnionMember;

public class Commissioned extends Employee {

    private Double fixedSalary;
    
    private Double commissionRate;

    private List<SaleReport> saleReports;
    

    public Commissioned(UUID id, String name, String address, UnionMember unionMember,
                        PaymentInfo paymentInfo, Double fixedSalary, Double commissionRate) {
        super(id, name, address, unionMember, paymentInfo);
        this.fixedSalary = fixedSalary;
        this.commissionRate = commissionRate;
        this.saleReports = new ArrayList<SaleReport>();
    }


    public Double getFixedSalary() {
        return this.fixedSalary;
    }

    public void setFixedSalary(Double fixedSalary) {
        this.fixedSalary = fixedSalary;
    }


    public Double getCommissionRate() {
        return this.commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }


    public List<SaleReport> getSaleReports() {
        return this.saleReports;
    }

    public void setSaleReports(List<SaleReport> saleReports) {
        this.saleReports = saleReports;
    }



    @Override
    public String toString() {
        return super.toString() + "\nCommissioned: {" +
            " fixedSalary='" + getFixedSalary() + "'" +
            ", commissionRate='" + getCommissionRate() + "%'" +
            ", saleReports='" + SaleReport.toString(this.saleReports) + "'" +
            "}";
    }


    @Override
    Double calcPayment(LocalDate paymentDate) {
        Double payment = (double) this.getFixedSalary() / (double) this.getPaymentInfo().getPaymentSchedule().getDividingFactor();
        List<SaleReport> validSales;
        List<Paycheck> paychecks = this.getPaymentInfo().getPaychecks();

        Predicate<SaleReport> dateFilter;

        if (paychecks != null && !paychecks.isEmpty()) {
            LocalDate lastPaymentDate = paychecks.get(paychecks.size() - 1).getDate();
            dateFilter = sale -> sale.getDate().isAfter(lastPaymentDate) && !sale.getDate().isAfter(paymentDate);
        } else {
            dateFilter = sale -> !sale.getDate().isAfter(paymentDate);
        }
        
        validSales = this.getSaleReports().stream().filter(dateFilter).collect(Collectors.toList());
        
        for (SaleReport s : validSales) {
            Double commission = s.getValue() * ((double) this.getCommissionRate() / 100.0);
            payment += commission;
        }
        
        return payment;
    }
}
