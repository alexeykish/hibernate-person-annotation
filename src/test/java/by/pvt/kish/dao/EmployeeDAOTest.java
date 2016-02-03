package by.pvt.kish.dao;

import by.pvt.kish.pojos.Employee;
import by.pvt.kish.pojos.EmployeeDetail;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


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

    private final static int MAX_UNIQUE_EMPLOYEE = 5;
    private final static int MAX_DUPLICATED_EMPLOYEE = 5;

    private final static String HQL_GET_ALL_EMPLOYEE = "FROM Employee";
    private final static String HQL_GET_ALL_EMPLOYEE_ORDERED_BY_ID_DESC = "SELECT E FROM Employee E ORDER BY E.employeeId DESC";
    private final static String HQL_GET_ALL_EMPLOYEE_GROUPED_BY_FIRSTNAME = "SELECT E FROM Employee E GROUP BY E.firstName";
    private final static String HQL_GET_EMPLOYEE__BY_FIRSTNAME = "SELECT E FROM Employee E WHERE E.firstName=:firstName";

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < MAX_UNIQUE_EMPLOYEE; i++) {
            employee = new Employee(FIRSTNAME + i, LASTNAME + i, PHONE + i, i * 10);
            employeeDetail = new EmployeeDetail(STREET + i, CITY + i, STATE + i, COUNTRY + i);

            employee.setEmployeeDetail(employeeDetail);
            employeeDetail.setEmployee(employee);

            eid = (Long) employeeDAO.saveOrUpdate(employee);
        }
        for (int i = 0; i < MAX_DUPLICATED_EMPLOYEE; i++) {
            employee = new Employee(FIRSTNAME, LASTNAME, PHONE, i * 10);
            employeeDetail = new EmployeeDetail(STREET, CITY, STATE, COUNTRY);

            employee.setEmployeeDetail(employeeDetail);
            employeeDetail.setEmployee(employee);

            eid = (Long) employeeDAO.saveOrUpdate(employee);
        }
    }

    @Test
    public void testGetAll() throws Exception {
        List<Employee> results = EmployeeDAO.getInstance().getAll(HQL_GET_ALL_EMPLOYEE);
        for (Employee e : results) {
            logger.info(e);
        }
    }

    @Test
    public void testGetAllOrderBy() throws Exception {
        List<Employee> results = EmployeeDAO.getInstance().getAll(HQL_GET_ALL_EMPLOYEE_ORDERED_BY_ID_DESC);
        for (Employee e : results) {
            logger.info(e);
        }
    }

    @Test
    public void testGetById() throws Exception {
        Employee employee = EmployeeDAO.getInstance().getById(eid);
        logger.info(employee);
    }

    @Test
    public void testGetByName() throws Exception {
        List<Employee> results = EmployeeDAO.getInstance().getByName(HQL_GET_EMPLOYEE__BY_FIRSTNAME, FIRSTNAME);
        for (Employee e : results) {
            logger.info(e);
        }
    }

    @Test
    public void testGetByNames() throws Exception {
        List<Employee> results = EmployeeDAO.getInstance().getByNames(FIRSTNAME, LASTNAME);
        for (Employee e : results) {
            logger.info(e);
        }
    }

    @Test
    public void testGetDetails() throws Exception {
        List<EmployeeDetail> employeeDetails = EmployeeDAO.getInstance().getDetails(eid);
        for (EmployeeDetail e : employeeDetails) {
            logger.info(e);
        }
    }

    @Test
    public void testGetAllGroupByFirstName() throws Exception {
        List<Employee> results = EmployeeDAO.getInstance().getAll(HQL_GET_ALL_EMPLOYEE_GROUPED_BY_FIRSTNAME);
        for (Employee e : results) {
            logger.info(e);
        }
    }

    @Test
    public void testGetAllCountGroupByFirstName() throws Exception {
        List results = EmployeeDAO.getInstance().getCount();
        for (Object result : results) {
            Object[] list = (Object[]) result;
            logger.info("Result (" + list[1] + " = " + list[0]);
        }
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        EmployeeDAO.getInstance().deleteEmployee(eid);
        assertNull("Delete cellPhone failed", EmployeeDAO.getInstance().getById(eid));
    }

    @Test
    public void testInsertEmployee() throws Exception {
        int before = EmployeeDAO.getInstance().getAll(HQL_GET_ALL_EMPLOYEE).size();
        EmployeeDAO.getInstance().insertEmployee(eid);
        int after = EmployeeDAO.getInstance().getAll(HQL_GET_ALL_EMPLOYEE).size();
        assertEquals("Insert employee failed", before, after - 1);
    }

    @Test
    public void testEmployeeAggregateMethods() throws Exception {
        int maxAge = EmployeeDAO.getInstance().getMaxEmployeeAge();
        Double avgAge = EmployeeDAO.getInstance().getAvgEmployeeAge();
        Long sumAge = EmployeeDAO.getInstance().getSumEmployeeAge();
        Long count = EmployeeDAO.getInstance().getCountEmployee();
        logger.info("Max age:" + maxAge);
        logger.info("Average age:" + avgAge);
        logger.info("Sum age:" + sumAge);
        logger.info("Count results:" + count);
    }

    @Test
    public void testPagination() throws Exception {
        int pageSize = 3;
        Long countResults = EmployeeDAO.getInstance().getCountEmployee();
        int lastPageNumber = (int) ((countResults / pageSize) + 1);
        for (int i = 1; i <= lastPageNumber; i++) {
            logger.info("Page #" + i);
            List<Employee> results = EmployeeDAO.getInstance().getAllToPage(pageSize, i);
            for (Employee e : results) {
                logger.info(e);
            }
            logger.info("------------------");
        }
    }

    @After
    public void tearDown() throws Exception {
        EmployeeDAO.getInstance().deleteAllEmployee();
    }
}