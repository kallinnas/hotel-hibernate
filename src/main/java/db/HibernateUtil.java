package db;

import entity.Guest;
import entity.Person;
import entity.Worker;
import model.Request;
import model.Reservation;
import model.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory initialFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Guest.class)
                .addAnnotatedClass(Worker.class)
                .addAnnotatedClass(Reservation.class)
                .addAnnotatedClass(Request.class)
                .addAnnotatedClass(Room.class)
                .buildSessionFactory();
    }

    public static Session getHibernateSession() {
        return initialFactory().openSession();
    }
}
