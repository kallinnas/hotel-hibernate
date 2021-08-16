package db.dao;

import db.HibernateUtils;
import entity.Guest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class GuestDaoImpl implements GuestDao{
    private Guest guest;
    private Query query;
    private Session session;
    private Transaction transaction;

    @Override
    public Guest getGuestById(long id) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            guest = session.get(Guest.class, id);
            transaction.commit();
        } finally {
            session.close();
        }
        return guest;
    }

    @Override
    public void persistGuest(Guest guest) {

    }

    @Override
    public void update(Guest guest) {
        session = HibernateUtils.getHibernateSession();
        try{
            transaction = session.beginTransaction();
            session.update(guest);
            transaction.commit();
        }finally {
            session.close();
        }
    }


}
