package by.pvt.kish.dao;

import by.pvt.kish.pojos.Department;

/**
 * @author Kish Alexey
 */
public class DepartmentDAO extends BaseDAO<Department> {

    private static DepartmentDAO instance;

    private DepartmentDAO() {
        super(Department.class);
    }

    public static DepartmentDAO getInstance() {
        if (instance == null) {
            instance = new DepartmentDAO();
        }
        return instance;
    }
}
