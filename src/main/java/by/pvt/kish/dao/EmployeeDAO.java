package by.pvt.kish.dao;

import by.pvt.kish.exception.DaoException;
import by.pvt.kish.pojos.Employee;
import by.pvt.kish.pojos.EmployeeDetail;
import by.pvt.kish.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kish Alexey
 */
public class EmployeeDAO extends BaseDAO<Employee> {

    private static EmployeeDAO instance;
    private HibernateUtil util = HibernateUtil.getUtil();
    private Transaction transaction = null;
    private static Logger logger = Logger.getLogger(BaseDAO.class);

    private EmployeeDAO() {
        super(Employee.class);
    }

    public static EmployeeDAO getInstance() {
        if (instance == null) {
            instance = new EmployeeDAO();
        }
        return instance;
    }

   public List<EmployeeDetail> getDetails(Long id) throws DaoException {
       List<EmployeeDetail> details = new ArrayList<>();
       try {
           Session session = util.getSession();
           transaction = session.beginTransaction();
           String hql = "SELECT E.employeeDetail FROM Employee E WHERE E.employeeId=:id";
           Query query = session.createQuery(hql);
           query.setParameter("id", id);
           details = query.list();
           transaction.commit();
       } catch (HibernateException e) {
           logger.error("Error in get by ID DAO", e);
           transaction.rollback();
           throw new DaoException(e);
       }
       return details;
   }

    public List<Employee> getByName(String hql, String eFirstName) throws DaoException {
        List<Employee> employees;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("firstName", eFirstName);
            employees = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in get by firstName DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return employees;
    }


    public List<Employee> getByNames(String hql, String firstName, String lastName) throws DaoException {
        List<Employee> employees;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            employees = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in get by firstName DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return employees;
    }
}
