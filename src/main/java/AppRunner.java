import db.HibernateUtil;
import db.ValidationUtils;
import entity.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AppRunner {
    public static void main(String[] args) {
        Person person1 = new Person("Serhii", "Kalinichenko", "k@gmail.com", 1000, 1);
//        Person person2 = new Person("Muha", "Abu", "ma@kd.com", 50, 2);
//        Person person3 = new Person("vik", "afek", "asdfasdf@kd.il", 50, 2);
//        Person person4 = new Person("Zull", "Tofer", "skach@kd.il", 50, 2);

        persistPerson(person1);

//        dropTables();

    }

    private static void persistPerson(Person person) {
        if (!ValidationUtils.hasViolation(person)) {

            Session session = HibernateUtil.getHibernateSession();
            try {
                Transaction transaction = session.beginTransaction();

                session.persist(person);

                Person guest = session.get(Person.class, 3L);
                System.out.println(guest);
                transaction.commit();
            } finally {
                session.close();
            }

        } else ValidationUtils.validate(person);


    }

    private static void dropTables() {
        String line;
        Session session = HibernateUtil.getHibernateSession();
        StringBuilder sb = new StringBuilder();

        try {
            Transaction transaction = session.beginTransaction();
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("src/main/resources/dropTables"));
            while ((line = bufferedReader.readLine()) != null){
                System.out.println(line);
                sb.append(line);
            }
            line = sb.toString();
            session.createSQLQuery(line).executeUpdate();
            transaction.commit();

        } catch (IOException e) {
            // ignore
        } finally {
            session.close();
        }
    }

}
