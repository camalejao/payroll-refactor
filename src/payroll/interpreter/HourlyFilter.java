package payroll.interpreter;

import payroll.model.employee.Employee;
import payroll.model.employee.Hourly;

public class HourlyFilter implements IEmployeeInterpreter {

    @Override
    public boolean isEmployeeEligible(Employee employee) {
        return employee instanceof Hourly;
    }
    
}
