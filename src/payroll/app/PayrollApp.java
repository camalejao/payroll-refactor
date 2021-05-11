package payroll.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Scanner;
import java.util.Stack;

import payroll.app.util.ConsoleUtils;
import payroll.interpreter.CommissionedFilter;
import payroll.interpreter.HourlyFilter;
import payroll.interpreter.UnionFilter;
import payroll.model.Company;

public class PayrollApp {
    public static void main(String[] args) {
        int option = 1;
        Scanner input = new Scanner(System.in);

        Company company = new Company();
        Stack<String> undoStack = new Stack<>();
        Stack<String> redoStack = new Stack<>();

        while (option != 0) {
            System.out.println("Payroll App Menu");
            System.out.println("[1] Register Employee");
            System.out.println("[2] List Employees");
            System.out.println("[3] Remove Employee");
            System.out.println("[4] Add Timecard");
            System.out.println("[5] Add Sale Report");
            System.out.println("[6] Add Service Tax");
            System.out.println("[7] Edit Employee");
            System.out.println("[8] Run Payroll");
            System.out.println("[9] Add new Payment Schedule");
            System.out.println("[10] Print Payment Reports");
            System.out.println("[11] Undo");
            System.out.println("[12] Redo");
            System.out.println("[0] Exit");

            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    undoStack.push(saveState(company));
                    company.addEmployee(EmployeeMenu.registerEmployee(input, company.getPaymentSchedules()));
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 2:
                    if (!company.isEmployeeListEmpty()) {
                        EmployeeMenu.printEmployees(company.getEmployees());
                    } else {
                        System.out.println("No employees registered to be listed");
                    }
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                    
                case 3:
                    if (!company.isEmployeeListEmpty()) {
                        undoStack.push(saveState(company));
                        company.removeEmployee(EmployeeMenu.removeEmployee(input, company.getEmployees()));
                    } else {
                        System.out.println("No employees registered to be removed");
                    }
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 4:
                    if (!company.isEmployeeListEmpty()) {
                        undoStack.push(saveState(company));
                        EmployeeMenu.addTimecard(input, company.getEmployees(new HourlyFilter()));
                    } else {
                        System.out.println("No employees registered to add it");
                    }
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 5:
                    if (!company.isEmployeeListEmpty()) {
                        undoStack.push(saveState(company));
                        EmployeeMenu.addSaleReport(input, company.getEmployees(new CommissionedFilter()));
                    } else {
                        System.out.println("No employees registered to add it");
                    }
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 6:
                    if (!company.isEmployeeListEmpty()) {
                        undoStack.push(saveState(company));
                        EmployeeMenu.addServiceTax(input, company.getEmployees(new UnionFilter()));
                    } else {
                        System.out.println("No employees registered to add it");
                    }
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 7:
                    if (!company.isEmployeeListEmpty()) {
                        undoStack.push(saveState(company));
                        EmployeeMenu.editEmployee(input, company.getEmployees(), company.getPaymentSchedules());
                    } else {
                        System.out.println("No employees registered to edit");
                    }
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 8:
                    if (!company.isEmployeeListEmpty()) {
                        undoStack.push(saveState(company));
                        company.addPaymentReports(PaymentsMenu.payroll(input, company.getEmployees()));
                    } else {
                        System.out.println("No employees registered to pay");
                    }
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 9:
                    undoStack.push(saveState(company));
                    company.addPaymentSchedule(PaymentsMenu.registerNewPaymentSchedule(input));
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 10:
                    company.printPaymentReports();
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 11:
                    if (!undoStack.isEmpty()) {
                        redoStack.push(saveState(company));
                        String state = undoStack.pop();
                        company = restoreState(state);
                    } else {
                        System.out.println("Nothing to Undo.");
                    }
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                case 12:
                    if (!redoStack.isEmpty()) {
                        undoStack.push(saveState(company));
                        String state = redoStack.pop();
                        company = restoreState(state);
                    } else {
                        System.out.println("Nothing to Redo.");
                    }
                    ConsoleUtils.pressEnterToContinue(input);
                    break;
                
                default:
                    break;
            }
            ConsoleUtils.clear();
        }
        input.close();
        System.out.println("Bye!");
    }

    private static String saveState(Company company) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(company);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not save state.");
            return "";
        }
    }

    private static Company restoreState(String state) {
        try {
            byte[] data = Base64.getDecoder().decode(state);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            return (Company) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not restore state.");
            return null;
        }
    }

}
