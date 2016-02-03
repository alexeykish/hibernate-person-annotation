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


    public List<Employee> getByNames(String firstName, String lastName) throws DaoException {
        List<Employee> employees;
        try {
            Session session = util.getSession();
            String hql = "SELECT E FROM Employee E WHERE E.firstName=:firstName AND E.lastName=:lastName";
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

    public void deleteEmployee(Long id) throws DaoException {
        try {
            Session session = util.getSession();
            String hql = "DELETE FROM Employee WHERE employeeId=:id";
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in update DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public void deleteAllEmployee() throws DaoException {
        try {
            Session session = util.getSession();
            String hql = "DELETE FROM Employee";
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in delete all employee DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public void insertEmployee(Long eid) throws DaoException {
        try {
            Session session = util.getSession();
            String hql = "INSERT INTO Employee (firstName, lastName, cellphone, age) " +
                    "SELECT firstName, lastName, cellphone, age " +
                    "FROM Employee WHERE employeeId=:id";
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("id", eid);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in insert DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public List getCount() throws DaoException {
        List results;
        try {
            Session session = util.getSession();
            String hql = "SELECT count(E), E.firstName FROM Employee E GROUP BY E.firstName";
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            results = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in get by firstName DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return results;
    }

    public int getMaxEmployeeAge() throws DaoException {
        int maxAge = 0;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            String hql = "SELECT max(E.age) FROM Employee E";
            Query query = session.createQuery(hql);
            maxAge = (int) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in max age DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return maxAge;
    }

    public Double getAvgEmployeeAge() throws DaoException {
        Double avgAge = null;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            String hql = "SELECT avg(E.age) FROM Employee E";
            Query query = session.createQuery(hql);
            avgAge = (Double) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in getavg age DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return avgAge;
    }

    public Long getSumEmployeeAge() throws DaoException {
        Long sumAge = null;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            String hql = "SELECT sum (E.age) FROM Employee E";
            Query query = session.createQuery(hql);
            sumAge = (Long) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in get sum age DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return sumAge;
    }

    public Long getCountEmployee() throws DaoException {
        Long count = null;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            String hql = "SELECT count (*) FROM Employee E";
            Query query = session.createQuery(hql);
            count = (Long) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in get count DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return count;
    }

    public List<Employee> getAllToPage(int pageSize, int pageNumber) throws DaoException {
        List<Employee> results;
        try {
            Session session = util.getSession();
            String hql = "FROM Employee";
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            results = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in get all to page DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return results;
    }
}
