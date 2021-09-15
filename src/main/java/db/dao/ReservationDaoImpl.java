package db.dao;

import db.HibernateUtils;
import model.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ReservationDaoImpl implements ReservationDao {

    private Query query;
    private Session session;
    private Transaction transaction;
    private List<Reservation> reservations;

    @Override
    public void persistReservation(Reservation reservation) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            session.persist(reservation);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Reservation> getReservation(String email) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            List list = session
                    .createNamedQuery("findReservationByEmail")
                    .setParameter("email", email).getResultList();
            return new ArrayList<Reservation>(list);
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public synchronized void updateReservation(Reservation reservation) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            session.update(reservation);
        } finally {
            transaction.commit();
            session.close();
        }
    }


}
