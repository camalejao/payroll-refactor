package payroll.model.payments;

import java.io.Serializable;
import java.time.LocalDate;

import payroll.model.employee.Employee;

public class Paycheck implements Serializable {
    
    private Employee employee;

    private LocalDate date;

    private Double grossPay;

    private Double deductions;

    private PaymentMethod paymentMethod;

    
    public Paycheck(Employee employee, LocalDate date, Double grossPay,
                    Double deductions) {
        this.employee = employee;
        this.date = date;
        this.grossPay = grossPay;
        this.deductions = deductions;
        this.paymentMethod = employee.getPaymentInfo().getPaymentMethod();
    }
    

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Double getGrossPay() {
        return this.grossPay;
    }

    public void setGrossPay(Double grossPay) {
        this.grossPay = grossPay;
    }


    public Double getDeductions() {
        return this.deductions;
    }

    public void setDeductions(Double deductions) {
        this.deductions = deductions;
    }


    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    

    public Double getNetPay() {
        return this.grossPay - this.deductions;
    }

    
    @Override
    public String toString() {
        String str = "\nEmployee: " + this.getEmployee().printBasicInfo();
        str += "\nDate: " + this.getDate();
        str += "\nGross Pay: " + this.getGrossPay();
        str += "\nDeductions: " + this.getDeductions();
        str += "\nNet Pay (gross - deductions): " + this.getNetPay();
        str += "\nPayment Method: " + this.getPaymentMethod().getMethodDescription() + "\n";
        
        return str;
    }
}
