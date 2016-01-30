package by.pvt.kish.dao;

import by.pvt.kish.pojos.EmployeeDetail;

/**
 * @author Kish Alexey
 */
public class EmployeeDetailDAO extends BaseDAO<EmployeeDetail> {

    private static EmployeeDetailDAO instance;

    private EmployeeDetailDAO() {
        super(EmployeeDetail.class);
    }

    public static EmployeeDetailDAO getInstance() {
        if (instance == null) {
            instance = new EmployeeDetailDAO();
        }
        return instance;
    }
}
