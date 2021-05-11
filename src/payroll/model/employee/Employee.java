package payroll.model.employee;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import payroll.model.payments.Paycheck;
import payroll.model.payments.PaymentInfo;
import payroll.model.union.UnionMember;

public abstract class Employee implements Serializable {

    private UUID id;

    private String name;

    private String address;

    private UnionMember unionMember;

    private PaymentInfo paymentInfo;

    
    public Employee() {

    }

    public Employee(UUID id, String name, String address,
                    UnionMember unionMember, PaymentInfo paymentInfo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.unionMember = unionMember;
        this.paymentInfo = paymentInfo;
    }
    

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    public UnionMember getUnionMember() {
        return this.unionMember;
    }

    public void setUnionMember(UnionMember unionMember) {
        this.unionMember = unionMember;
    }


    public PaymentInfo getPaymentInfo() {
        return this.paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }


    private String appendUnionMemberString() {
        if (getUnionMember() != null) {
            return getUnionMember().toString();
        } else {
            return "\nUnion: not a union member";
        }
    }

    @Override
    public String toString() {
        return "Employee ID: " + getId() +
            "\nName: " + getName() +
            "\nAddress: " + getAddress() +
            "\nPayment Info: " + getPaymentInfo().toString() +
            appendUnionMemberString();
    }

    public String printBasicInfo() {
        return this.getName() + " id:" + this.getId();
    }


    public Double getUnionFee() {
        UnionMember unionMember = this.getUnionMember();
        
        if (unionMember != null && unionMember.isActive()) {
            return unionMember.getFee();
        } else {
            return 0.0;
        }
    }


    public Double calcServiceTaxes() {
        Double taxes = 0.0;
        UnionMember unionMember = this.getUnionMember();
        
        if (unionMember != null) {
            Paycheck lastPayment = this.getPaymentInfo().getLastPayment();
            
            if (lastPayment != null) {
                taxes += unionMember.getServiceTaxesSumAfterDate(lastPayment.getDate());
            } else {
                taxes = unionMember.getServiceTaxesSum();
            }
        }

        return taxes;
    }

    public Double calcDeductions(LocalDate paymentDate) {
        Double serviceTaxes = this.calcServiceTaxes();
        Double unionFee = this.getUnionFee();

        Paycheck lastPayment = this.getPaymentInfo().getLastPayment();

        if (lastPayment != null) {
            LocalDate lastPaymentDate = lastPayment.getDate();
            
            // checks if union fee was included in a payment in the same month
            if (!(lastPaymentDate.getMonthValue() == paymentDate.getMonthValue()
                && lastPaymentDate.getYear() == paymentDate.getYear())) {
                return serviceTaxes + unionFee;
            }

        } else {
            return serviceTaxes + unionFee;
        }
        
        return serviceTaxes;
    }

    public Paycheck processPayment(LocalDate paymentDate) {
        Double grossPay = this.calcPayment(paymentDate);
        Double deductions = this.calcDeductions(paymentDate); 

        Paycheck newPaycheck = new Paycheck(this, paymentDate, grossPay, deductions);

        this.getPaymentInfo().getPaychecks().add(newPaycheck);

        return newPaycheck;
    }

    abstract Double calcPayment(LocalDate paymentDate);
}
