package db;

import entity.Employee;
import entity.Guest;
import entity.Person;
import model.Request;
import model.Reservation;
import model.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    public static Session getHibernateSession() {
        return initialFactory().openSession();
    }

    private static SessionFactory initialFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Guest.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Reservation.class)
                .addAnnotatedClass(Request.class)
                .addAnnotatedClass(Room.class)
                .buildSessionFactory();
    }

}
