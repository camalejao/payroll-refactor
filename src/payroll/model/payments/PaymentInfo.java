package payroll.model.payments;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentInfo implements Serializable {
    
    private int bank;

    private int agency;

    private int account;

    private PaymentMethod paymentMethod;

    private PaymentSchedule paymentSchedule;

    private List<Paycheck> paychecks;


    public PaymentInfo(int bank, int agency, int account,
                    PaymentMethod paymentMethod, PaymentSchedule paymentSchedule) {
        this.bank = bank;
        this.agency = agency;
        this.account = account;
        this.paymentMethod = paymentMethod;
        this.paymentSchedule = paymentSchedule;
        this.paychecks = new ArrayList<Paycheck>();
    }


    public int getBank() {
        return this.bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }


    public int getAgency() {
        return this.agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }


    public int getAccount() {
        return this.account;
    }

    public void setAccount(int account) {
        this.account = account;
    }


    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public PaymentSchedule getPaymentSchedule() {
        return this.paymentSchedule;
    }

    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }


    public List<Paycheck> getPaychecks() {
        return this.paychecks;
    }

    public void setPaychecks(List<Paycheck> paychecks) {
        this.paychecks = paychecks;
    }
    

    @Override
    public String toString() {
        return "{" +
            " bank='" + getBank() + "'" +
            ", agency='" + getAgency() + "'" +
            ", account='" + getAccount() + "'" +
            ", schedule='" + getPaymentSchedule().toString() + "'" +
            ", paymentMethod='" + getPaymentMethod().getMethodDescription() + "'" +
            "}";
    }


    public Paycheck getLastPayment() {
        Paycheck lastPayment = null;

        if (this.paychecks != null && !this.paychecks.isEmpty()) {
            lastPayment = this.paychecks.get(this.paychecks.size() - 1);
        }

        return lastPayment;
    }

    private boolean haveBeenPaidThisDate(LocalDate date) {
        for (Paycheck paycheck : this.paychecks) {
            if (paycheck.getDate().isEqual(date)) {
                String msg = "Employee " + paycheck.getEmployee().printBasicInfo();
                msg += " matched this payment date (" + date.toString() + ")";
                msg += ", but has already been paid.\n";
                System.out.println(msg);
                return true;
            }
        }
        return false;
    }

    public boolean isPaymentDay(int weekCounter, LocalDate currentDate) {
        return (!this.haveBeenPaidThisDate(currentDate)
            && this.paymentSchedule.checkIfDateIsInSchedule(weekCounter, currentDate));
    }
}
