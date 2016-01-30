package by.pvt.kish;

import by.pvt.kish.dao.DepartmentDAO;
import by.pvt.kish.dao.EmployeeDAO;
import by.pvt.kish.pojos.Department;
import by.pvt.kish.pojos.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Kish Alexey
 */
public class OneToManyTest {

    private Employee employee1;
    private Employee employee2;
    private Department department;
    private Long eid1;
    private Long eid2;
    private Long eid3;
    private Long did;
    private EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
    private DepartmentDAO departmentDAO = DepartmentDAO.getInstance();

    @Before
    public void setUp() throws Exception {
        department = new Department("TEST");
        did = (Long) departmentDAO.saveOrUpdate(department);

        employee1 = new Employee("TEST", "TEST", "xxx-xxx-xxx");
        employee2 = new Employee("TEST", "TEST", "xxx-xxx-xxx");

        employee1.setDepartment(department);
        employee2.setDepartment(department);

        eid1 = (Long) employeeDAO.saveOrUpdate(employee1);
        eid2 = (Long) employeeDAO.saveOrUpdate(employee2);
    }

    @Test
    public void testAdd() throws Exception {
        department.setDepartmentId(did);

        Employee addedEmployee1 = employeeDAO.get(eid1);
        Employee addedEmployee2 = employeeDAO.get(eid2);
        Department addedDepartment = departmentDAO.get(did);

        assertEquals("Add method failed: wrong department name", addedDepartment.getDepartmentName(), department.getDepartmentName());
        assertEquals("Add method failed: wrong employees", addedDepartment.getEmployees().size(), department.getEmployees().size());
        assertEquals("Add method failed: wrong department", addedEmployee1.getDepartment(), employee1.getDepartment());
        assertEquals("Add method failed: wrong department", addedEmployee2.getDepartment(), employee2.getDepartment());
    }

    @Test
    public void testUpdate() throws Exception {
        Department preUpdatedDepartment = departmentDAO.get(did);
        Employee employee3 = new Employee("UPDATE", "UPDATE", "XXX-XXX-XXX");
        preUpdatedDepartment.setDepartmentName("UPDATE");
        preUpdatedDepartment.getEmployees().add(employee3);
        departmentDAO.saveOrUpdate(preUpdatedDepartment);

        employee3.setDepartment(department);
        Department updatedDepartment = departmentDAO.get(did);
        eid3 = (Long) employeeDAO.saveOrUpdate(employee3);
        Employee updatedEmployee = employeeDAO.get(eid3);

        assertEquals("Add method failed: wrong department name", preUpdatedDepartment.getDepartmentName(), updatedDepartment.getDepartmentName());
        assertEquals("Add method failed: wrong employees", preUpdatedDepartment.getEmployees().size(), updatedDepartment.getEmployees().size());
        assertEquals("Add method failed: wrong department", employee3.getDepartment(), updatedEmployee.getDepartment());
    }

    @Test
    public void testDelete() throws Exception {
        employeeDAO.delete(eid1);
        employeeDAO.delete(eid2);
        departmentDAO.delete(did);

        assertNull(employeeDAO.get(eid1));
        assertNull(employeeDAO.get(eid2));
        assertNull(departmentDAO.get(did));
    }

    @After
    public void tearDown() throws Exception {

    }

}
