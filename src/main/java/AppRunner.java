import db.HibernateUtil;
import db.dao.*;
import entity.Employee;
import entity.Guest;
import entity.Person;
import model.Request;
import model.RequestType;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AppRunner {
    public static void main(String[] args) {
        Person person1 = new Person("Serhii", "Kalinichenko", "k@gmail.com", 1000, 1);
        Person person2 = new Person("Muha", "Abu", "ma@kd.com", 50, 2);
//        Person person3 = new Person("vik", "afek", "asdfasdf@kd.il", 50, 2);
//        Person person4 = new Person("Zull", "Tofer", "skach@kd.il", 50, 2);

        PersonDao personDao = new PersonDaoImpl();
        GuestDao guestDao = new GuestDaoImpl();
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        RequestDao requestDao = new RequestDaoImpl();
//
//        personDao.persistPerson(person1);
//        personDao.persistPerson(person2);

        Guest g = guestDao.getGuestById(1);
        Employee e = employeeDao.getEmployeeById(1);

        Request request = g.createRequest(RequestType.ROOM_SERVICE);
        Request request1 = g.createRequest(RequestType.CHECK_IN);

        requestDao.persistRequest(request);
        requestDao.persistRequest(request1);

        Thread threadE = new Thread(e);
        threadE.start();



//        dropTables();

    }


    private static void dropTables() {
        String line;
        Session session = HibernateUtil.getHibernateSession();
        StringBuilder sb = new StringBuilder();

        try {
            Transaction transaction = session.beginTransaction();
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("src/main/resources/dropTables"));
            while ((line = bufferedReader.readLine()) != null) {
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
