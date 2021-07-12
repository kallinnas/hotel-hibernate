package db.dao;

import db.HibernateUtil;
import model.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RequestDaoImpl implements RequestDao {


    @Override
    public void persistRequest(Request request) {
        Session session = HibernateUtil.getHibernateSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.persist(request);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateRequest(Request request) {
        Session session = HibernateUtil.getHibernateSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.update(request);
            transaction.commit();
        } finally {
            session.close();
        }
    }

}
