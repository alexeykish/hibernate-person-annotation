package by.pvt.kish;

import by.pvt.kish.dao.EmployeeDAO;
import by.pvt.kish.dao.MeetingDAO;
import by.pvt.kish.pojos.Employee;
import by.pvt.kish.pojos.Meeting;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Kish Alexey
 */
public class ManyToManyTest {

    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Meeting meeting1;
    private Meeting meeting2;
    private Long eid1;
    private Long eid2;
    private Long eid3;
    private Long mid1;
    private Long mid2;
    private EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
    private MeetingDAO meetingDAO = MeetingDAO.getInstance();

    @Before
    public void setUp() throws Exception {

        meeting1 = new Meeting("TEST1");
        meeting2 = new Meeting("TEST2");
        mid1 = (Long) meetingDAO.saveOrUpdate(meeting1);
        mid2 = (Long) meetingDAO.saveOrUpdate(meeting2);

        employee1 = new Employee("TEST", "TEST", "xxx-xxx-xxx");
        employee2 = new Employee("TEST", "TEST", "xxx-xxx-xxx");
        employee3 = new Employee("TEST", "TEST", "xxx-xxx-xxx");

        employee1.getMeetings().add(meeting1);
        employee1.getMeetings().add(meeting2);
        employee2.getMeetings().add(meeting1);
        employee3.getMeetings().add(meeting2);

        eid1 = (Long) employeeDAO.saveOrUpdate(employee1);
        eid2 = (Long) employeeDAO.saveOrUpdate(employee2);
        eid3 = (Long) employeeDAO.saveOrUpdate(employee3);
    }

    @Test
    public void testAdd() throws Exception {

        Employee addedEmployee1 = employeeDAO.get(eid1);
        Employee addedEmployee2 = employeeDAO.get(eid2);
        Employee addedEmployee3 = employeeDAO.get(eid3);

        Meeting addedMeeting1 = meetingDAO.get(mid1);
        Meeting addedMeeting2 = meetingDAO.get(mid2);

        assertEquals("Add method failed: wrong meeting", addedEmployee1.getMeetings().size(), employee1.getMeetings().size());
        assertEquals("Add method failed: wrong meeting", addedEmployee2.getMeetings().size(), employee2.getMeetings().size());
        assertEquals("Add method failed: wrong meeting", addedEmployee3.getMeetings().size(), employee3.getMeetings().size());
        assertEquals("Add method failed: wrong meeting", addedMeeting1.getEmployees().size(), meeting1.getEmployees().size());
        assertEquals("Add method failed: wrong meeting", addedMeeting2.getEmployees().size(), meeting2.getEmployees().size());
    }

    @Test
    public void testUpdate() throws Exception {
        Meeting meeting3 = new Meeting("TEST3");
        Long mid3 = (Long) meetingDAO.saveOrUpdate(meeting3);

        Employee preUpdatedEmployee1 = employeeDAO.get(eid1);
        Employee preUpdatedEmployee2 = employeeDAO.get(eid2);
        Employee preUpdatedEmployee3 = employeeDAO.get(eid3);

        preUpdatedEmployee1.getMeetings().add(meeting3);
        preUpdatedEmployee2.getMeetings().add(meeting3);
        preUpdatedEmployee3.getMeetings().add(meeting3);

        employeeDAO.saveOrUpdate(preUpdatedEmployee1);
        employeeDAO.saveOrUpdate(preUpdatedEmployee2);
        employeeDAO.saveOrUpdate(preUpdatedEmployee3);

        Employee updatedEmployee1 = employeeDAO.get(eid1);
        Employee updatedEmployee2 = employeeDAO.get(eid2);
        Employee updatedEmployee3 = employeeDAO.get(eid3);

        Meeting updatedMeeting3 = meetingDAO.get(mid3);
        assertEquals("Update method failed: wrong meeting", updatedEmployee1.getMeetings().size(), preUpdatedEmployee1.getMeetings().size());
        assertEquals("Update method failed: wrong meeting", updatedEmployee2.getMeetings().size(), preUpdatedEmployee2.getMeetings().size());
        assertEquals("Update method failed: wrong meeting", updatedEmployee3.getMeetings().size(), preUpdatedEmployee3.getMeetings().size());
        assertEquals("Update method failed: wrong meeting", updatedMeeting3.getEmployees().size(), meeting3.getEmployees().size());
    }

    @Test
    public void testDelete() throws Exception {
        employeeDAO.delete(eid1);
        employeeDAO.delete(eid2);
        employeeDAO.delete(eid3);
        meetingDAO.delete(mid1);
        meetingDAO.delete(mid2);

        assertNull(employeeDAO.get(eid1));
        assertNull(employeeDAO.get(eid1));
        assertNull(employeeDAO.get(eid2));
        assertNull(meetingDAO.get(mid1));
        assertNull(meetingDAO.get(mid2));
    }

    @After
    public void tearDown() throws Exception {

    }

}
