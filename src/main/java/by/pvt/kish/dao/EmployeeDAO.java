package by.pvt.kish.dao;

import by.pvt.kish.pojos.Employee;

/**
 * @author Kish Alexey
 */
public class EmployeeDAO extends BaseDAO<Employee> {

    private static EmployeeDAO instance;

    private EmployeeDAO() {
        super(Employee.class);
    }

    public static EmployeeDAO getInstance() {
        if (instance == null) {
            instance = new EmployeeDAO();
        }
        return instance;
    }
}
