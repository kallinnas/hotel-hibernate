package db.dao;

import db.HibernateUtil;
import entity.Guest;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GuestDaoImpl implements GuestDao{
    private Guest guest;

    @Override
    public Guest getGuestById(long id) {
        Session session = HibernateUtil.getHibernateSession();
        try {
            Transaction transaction = session.beginTransaction();
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
}
