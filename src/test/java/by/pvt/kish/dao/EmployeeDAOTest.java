package by.pvt.kish.dao;

import by.pvt.kish.pojos.Employee;
import by.pvt.kish.pojos.EmployeeDetail;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Kish Alexey
 */
public class EmployeeDAOTest {

    private static Logger logger = Logger.getLogger(EmployeeDAOTest.class);
    private Employee employee;
    private EmployeeDetail employeeDetail;
    private Long eid;
    private EmployeeDAO employeeDAO = EmployeeDAO.getInstance();

    @Before
    public void setUp() throws Exception {
        employee = new Employee("HQL", "HQL", "xxx-xxx-xxx");
        employeeDetail = new EmployeeDetail("HQL", "HQL", "HQL", "HQL");

        employee.setEmployeeDetail(employeeDetail);
        employeeDetail.setEmployee(employee);

        eid = (Long) employeeDAO.saveOrUpdate(employee);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {
        List<Employee> results = EmployeeDAO.getInstance().getAll("FROM Employee");
        for (Employee e: results) {
            logger.info(e);
        }
    }

    @Test
    public void testGetById() throws  Exception {
        String hql = "SELECT E FROM Employee E WHERE E.employeeId=:id";
        Employee employee = EmployeeDAO.getInstance().getById(hql, eid);
        logger.info(employee);
    }

    @Test
    public void testGetDetails() throws  Exception {
        List<EmployeeDetail> employeeDetails = EmployeeDAO.getInstance().getDetails(eid);
        for (EmployeeDetail e: employeeDetails) {
            logger.info(e);
        }
    }
}