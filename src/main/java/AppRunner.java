import entity.Guest;
import entity.Person;
import entity.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class AppRunner {
    public static void main(String[] args) {
        Person person = new Person("Serhii",
                "Kalinichenko",
                "k@gmail.com", 1000, 1);


        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Guest.class)
                .addAnnotatedClass(Worker.class)
                .buildSessionFactory();

        try {
            Session session = factory.getCurrentSession();
            Transaction transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
        } finally {
            factory.close();
        }

        System.out.println(person.getId());
    }
}
