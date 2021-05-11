package payroll.interpreter;

import payroll.model.employee.Commissioned;
import payroll.model.employee.Employee;

public class CommissionedFilter implements IEmployeeInterpreter {

    @Override
    public boolean isEmployeeEligible(Employee employee) {
        return employee instanceof Commissioned;
    }
    
}
