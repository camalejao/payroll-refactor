package payroll.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import payroll.interpreter.IEmployeeInterpreter;
import payroll.model.employee.Commissioned;
import payroll.model.employee.Employee;
import payroll.model.employee.Hourly;
import payroll.model.employee.Salaried;
import payroll.model.payments.PaymentSchedule;
import payroll.model.payments.PaymentsReport;
import payroll.model.payments.Schedule;

public class Company implements Serializable {
    
    List<PaymentsReport> payments;
    
    List<Employee> employees;
    
    List<PaymentSchedule> paymentSchedules;


    public Company() {
        this.payments = new ArrayList<PaymentsReport>();
        this.employees = new ArrayList<Employee>();
        this.paymentSchedules = new ArrayList<PaymentSchedule>();

        // default payment schedules
        this.paymentSchedules.add(new PaymentSchedule(Schedule.WEEKLY, null, DayOfWeek.FRIDAY));
        this.paymentSchedules.add(new PaymentSchedule(Schedule.MONTHLY, null, null));
        this.paymentSchedules.add(new PaymentSchedule(Schedule.BIWEEKLY, null, DayOfWeek.FRIDAY));
    }


    public List<PaymentsReport> getPayments() {
        return this.payments;
    }

    public void setPayments(List<PaymentsReport> payments) {
        this.payments = payments;
    }


    public List<Employee> getEmployees() {
        return this.employees;
    }

    public List<Employee> getEmployees(IEmployeeInterpreter interpreter) {
        List<Employee> employeeList = new ArrayList<>();

        for (Employee employee : this.employees) {
            if (interpreter.isEmployeeEligible(employee)) {
                employeeList.add(employee);
            }
        }
        
        return employeeList;
    }

    public List<Employee> getHourlyEmployees() {
        Predicate<Employee> hourlyFilter = employee -> employee instanceof Hourly;
        List<Employee> hourlyEmployees = this.employees.stream().filter(hourlyFilter).collect(Collectors.toList());
        
        return hourlyEmployees;
    }

    public List<Employee> getSalariedEmployees() {
        Predicate<Employee> salariedFilter = employee -> employee instanceof Salaried;
        List<Employee> salariedEmployees = this.employees.stream().filter(salariedFilter).collect(Collectors.toList());
        
        return salariedEmployees;
    }

    public List<Employee> getCommissionedEmployees() {
        Predicate<Employee> commissionedFilter = employee -> employee instanceof Commissioned;
        List<Employee> commissionedEmployees = this.employees.stream().filter(commissionedFilter).collect(Collectors.toList());
        
        return commissionedEmployees;
    }

    public List<Employee> getUnionMemberEmployees() {
        Predicate<Employee> unionFilter = employee -> employee.getUnionMember() != null && employee.getUnionMember().isActive();
        List<Employee> unionEmployees = this.employees.stream().filter(unionFilter).collect(Collectors.toList());
        
        return unionEmployees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    public List<PaymentSchedule> getPaymentSchedules() {
        return this.paymentSchedules;
    }

    public void setPaymentSchedules(List<PaymentSchedule> paymentSchedules) {
        this.paymentSchedules = paymentSchedules;
    }

    
    public boolean isEmployeeListEmpty() {
        return this.employees.isEmpty();
    }


    public void addEmployee(Employee e) {
        this.employees.add(e);
    }


    public void addPaymentSchedule(PaymentSchedule ps) {
        this.paymentSchedules.add(ps);
        System.out.println("Schedule '" + ps.toString() + "' added.");
    }


    public void removeEmployee(int employeeIndex) {
        this.employees.remove(employeeIndex);
        System.out.println("Employee removed.");
    }


	public void addPaymentReports(List<PaymentsReport> payroll) {
        if (!payroll.isEmpty()) {
            this.payments.addAll(payroll);
            System.out.println("Reports from payroll execution stored.");
        } else {
            System.out.println("Nothing to be stored from payroll.");
        }
	}


    public void printPaymentReports() {
        if (this.payments.isEmpty()) {
            System.out.println("No payments stored.");
            return;
        }

        for (PaymentsReport pr : this.payments) {
            System.out.println(pr.toString());
            System.out.println("====================================");
        }
    }

}
