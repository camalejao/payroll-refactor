package payroll.interpreter;

import payroll.model.employee.Employee;
import payroll.model.employee.Salaried;

public class SalariedFilter implements IEmployeeInterpreter {

    @Override
    public boolean isEmployeeEligible(Employee employee) {
        return employee instanceof Salaried;
    }
    
}
