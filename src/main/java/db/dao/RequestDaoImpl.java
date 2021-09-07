package db.dao;

import db.HQLSchema;
import db.HibernateUtils;
import model.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RequestDaoImpl implements RequestDao {

    private Session session;
    private Transaction transaction;

    @Override
    public void persistRequest(Request request) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            session.persist(request);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateRequest(Request request) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            session.update(request);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public Request getLastCreatedRequest() {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            List list = session.createQuery(HQLSchema.SELECT_LAST_INSERTED_REQUEST).list();
            return new ArrayList<Request>(list).stream().findFirst().orElse(null);
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Request> getAllRequests() {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            return session.createQuery(HQLSchema.SELECT_ALL_REQUESTS, Request.class)
                    .getResultList();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public synchronized Request getTheOldestUndoneRequest() {
        session = HibernateUtils.getHibernateSession().getSession();
        try {
            transaction = session.beginTransaction();
            List<Request> result = session.createQuery(HQLSchema.SELECT_ALL_UNDONE_REQUEST, Request.class)
                    .getResultList();
            if (result.size() > 0) return result.get(0);
            else return null;
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public Request getRequestById(long id) {
        session = HibernateUtils.getHibernateSession().getSession();
        try {
            transaction = session.beginTransaction();
            return session.createQuery(HQLSchema.SELECT_REQUEST_BY_ID, Request.class)
                    .setParameter("id", id)
                    .setMaxResults(1).uniqueResult();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public Request getUnfinishedRequestByEmployeeId(long id) {
        session = HibernateUtils.getHibernateSession().getSession();
        try {
            transaction = session.beginTransaction();
            return session.createQuery(HQLSchema.SELECT_UNFINISHED_REQUEST, Request.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } finally {
            transaction.commit();
            session.close();
        }

    }


}
