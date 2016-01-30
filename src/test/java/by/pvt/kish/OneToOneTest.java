package by.pvt.kish;

import by.pvt.kish.dao.EmployeeDAO;
import by.pvt.kish.dao.EmployeeDetailDAO;
import by.pvt.kish.pojos.Employee;
import by.pvt.kish.pojos.EmployeeDetail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Kish Alexey
 */
public class OneToOneTest {

    private Employee employee;
    private EmployeeDetail employeeDetail;
    private Long eid;
    private EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
    private EmployeeDetailDAO employeeDetailDAO = EmployeeDetailDAO.getInstance();

    @Before
    public void setUp() throws Exception {
        employee = new Employee("TEST", "TEST", "xxx-xxx-xxx");
        employeeDetail = new EmployeeDetail("TEST", "TEST", "TEST", "TEST");

        employee.setEmployeeDetail(employeeDetail);
        employeeDetail.setEmployee(employee);

        eid = (Long) employeeDAO.saveOrUpdate(employee);
    }

    @Test
    public void testAdd() throws Exception {
        employee.setEmployeeId(eid);
        employeeDetail.setEmployeeId(eid);

        Employee addedEmployee = employeeDAO.get(eid);

        assertEquals("Add method failed: wrong employee", addedEmployee, employee);
        assertEquals("Add method failed: wrong employeeDetails", addedEmployee.getEmployeeDetail(), employeeDetail);
    }

    @Test
    public void testUpdate() throws Exception {
        Employee preUpdatedEmployee = employeeDAO.get(eid);
        preUpdatedEmployee.setFirstName("UPDATE");
        preUpdatedEmployee.setLastName("UPDATE");
        preUpdatedEmployee.setCellphone("XXXX-XXX-XXX");

        EmployeeDetail preUpdatedEmployeeDetail = employeeDetailDAO.get(eid);
        preUpdatedEmployeeDetail.setStreet("UPDATE");
        preUpdatedEmployeeDetail.setCity("UPDATE");
        preUpdatedEmployeeDetail.setState("UPDATE");
        preUpdatedEmployeeDetail.setCountry("UPDATE");

        preUpdatedEmployee.setEmployeeDetail(preUpdatedEmployeeDetail);
        preUpdatedEmployeeDetail.setEmployee(preUpdatedEmployee);

        employeeDAO.saveOrUpdate(preUpdatedEmployee);
        Employee updatedEmployee = employeeDAO.get(eid);

        assertEquals("Update method failed: wrong employee", preUpdatedEmployee, updatedEmployee);
        assertEquals("Update method failed: wrong employeeDetails", preUpdatedEmployeeDetail, updatedEmployee.getEmployeeDetail());

    }

    @Test
    public void testDelete() throws Exception {
        employeeDAO.delete(eid);

        assertNull(employeeDAO.get(eid));
        assertNull(employeeDetailDAO.get(eid));
    }

    @After
    public void tearDown() throws Exception {

    }

}
