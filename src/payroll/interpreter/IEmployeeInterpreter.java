package payroll.interpreter;

import payroll.model.employee.Employee;

public interface IEmployeeInterpreter {
    
    boolean isEmployeeEligible(Employee employee);

}
