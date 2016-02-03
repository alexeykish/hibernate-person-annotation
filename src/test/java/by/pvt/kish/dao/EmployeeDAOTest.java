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

    private final static String FIRSTNAME = "eFirstName";
    private final static String LASTNAME = "eLastName";
    private final static String PHONE = "ePhone";
    private final static String STREET = "edStreet";
    private final static String CITY = "edCity";
    private final static String STATE = "edState";
    private final static String COUNTRY = "edCountry";

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 5; i++) {
            employee = new Employee(FIRSTNAME + i, LASTNAME + i, PHONE + i);
            employeeDetail = new EmployeeDetail(STREET + i, CITY + i, STATE + i, COUNTRY + i);

            employee.setEmployeeDetail(employeeDetail);
            employeeDetail.setEmployee(employee);

            eid = (Long) employeeDAO.saveOrUpdate(employee);
        }
        for (int i = 0; i < 3; i++) {
            employee = new Employee(FIRSTNAME, LASTNAME, PHONE);
            employeeDetail = new EmployeeDetail(STREET, CITY, STATE, COUNTRY);

            employee.setEmployeeDetail(employeeDetail);
            employeeDetail.setEmployee(employee);

            eid = (Long) employeeDAO.saveOrUpdate(employee);
        }
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
    public void testGetAllOrderBy() throws Exception {
        String hql = "SELECT E FROM Employee E ORDER BY E.employeeId DESC";
        List<Employee> results = EmployeeDAO.getInstance().getAll(hql);
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
    public void testGetByName() throws  Exception {
        String hql = "SELECT E FROM Employee E WHERE E.firstName=:firstName";
        List<Employee> results = EmployeeDAO.getInstance().getByName(hql, FIRSTNAME);
        for (Employee e: results) {
            logger.info(e);
        }
    }

    @Test
    public void testGetByNames() throws  Exception {
        String hql = "SELECT E FROM Employee E WHERE E.firstName=:firstName AND E.lastName=:lastName";
        List<Employee> results = EmployeeDAO.getInstance().getByNames(hql, FIRSTNAME, LASTNAME);
        for (Employee e: results) {
            logger.info(e);
        }
    }

    @Test
    public void testGetDetails() throws  Exception {
        List<EmployeeDetail> employeeDetails = EmployeeDAO.getInstance().getDetails(eid);
        for (EmployeeDetail e: employeeDetails) {
            logger.info(e);
        }
    }

    @Test
    public void testGetAllGroupByFirstName() throws  Exception {
        String hql = "SELECT E FROM Employee E GROUP BY E.firstName";
        List<Employee> results = EmployeeDAO.getInstance().getAll(hql);
        for (Employee e: results) {
            logger.info(e);
        }
    }

    @Test
    public void testGetAllCountGroupByFirstName() throws  Exception {
        String hql = "SELECT count(E), E.firstName FROM Employee E GROUP BY E.firstName";
        List results = EmployeeDAO.getInstance().getCount(hql);
        for (Object result: results) {
            Object [] list = (Object[]) result;
            logger.info("Result (" + list[1] + " = " + list[0]);
        }
    }
}