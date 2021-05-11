package payroll.interpreter;

import payroll.model.employee.Employee;

public class UnionFilter implements IEmployeeInterpreter {

    @Override
    public boolean isEmployeeEligible(Employee employee) {
        return employee.getUnionMember() != null && employee.getUnionMember().isActive();
    }
    
}
