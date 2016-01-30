package by.pvt.kish.dao;

import by.pvt.kish.exception.DaoException;
import by.pvt.kish.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

/**
 * @author Kish Alexey
 */
public class BaseDAO<T> implements DAO<T> {

    private static Logger logger = Logger.getLogger(BaseDAO.class);
    private Class className;

    private HibernateUtil util = HibernateUtil.getUtil();
    private Transaction transaction = null;

    public BaseDAO(Class<T> className) {
        this.className = className;
    }

    public Serializable saveOrUpdate(T t) throws DaoException {
        Serializable iid;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(t);
            transaction.commit();
            iid = session.getIdentifier(t);
            logger.info("Generated ID: " + iid);
            logger.info("Saved object: " + t);
        } catch (HibernateException e) {
            logger.error("Error in save or update DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return iid;
    }

    public T get(Serializable id) throws  DaoException {
        T t = null;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            t = (T) session.get(className, id);
            logger.info("Get object: " + t);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in save or update DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return t;
    }

    public T load(Serializable id) throws  DaoException {
        T t = null;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            t = (T) session.load(className, id);
            logger.info(t);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error in save or update DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return t;
    }

    public void delete(Serializable id) throws DaoException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            T t = (T) session.get(className, id);
            if (t == null) {
                throw  new DaoException("Cant find object with that ID: " + id);
            }
            session.delete(t);
            transaction.commit();
            logger.info("Deleted object: " + t);
        } catch (HibernateException e) {
            logger.error("Error in delete DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public Serializable getIdentifier(T t) throws  DaoException {
        Serializable id;
        try {
            Session session = util.getSession();
            id = session.getIdentifier(t);
            logger.info("ID: " + id);
        } catch (HibernateException e) {
            logger.error("Error in getIdentifier DAO", e);
            transaction.rollback();
            throw new DaoException(e);
        }
        return id;
    }
}
