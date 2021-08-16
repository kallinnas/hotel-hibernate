import db.HibernateUtils;
import model.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class Emulator {

    private ReservationService reservationService;
    private EmployeeService employeeService;
    private RoomService roomService;

    public Emulator() {
        this.reservationService = new ReservationServiceImpl();
        this.employeeService = new EmployeeServiceImpl();
        this.roomService = new RoomServiceImpl();
    }

    public void emulateReservations() {
        reservationService.createReservation("Serhii", "Kalinichenko",
                "k@gmail.com", true, 1000,
                LocalDateTime.of(2021, 7, 15, 14, 14, 38),
                LocalDateTime.of(2021, 7, 17, 20, 0, 38));

        reservationService.createReservation("Muhammad", "Abuhdir",
                "muha@gmail.com", true, 1000,
                LocalDateTime.of(2021, 7, 15, 14, 14, 38),
                LocalDateTime.of(2021, 7, 17, 20, 0, 38));

        reservationService.createReservation("Romeo", "Jo",
                "romeo@gmail.com", true, 1000,
                LocalDateTime.of(2021, 7, 15, 14, 14, 38),
                LocalDateTime.of(2021, 7, 17, 20, 0, 38));

        reservationService.createReservation("Julieta", "Rork",
                "jul+@gmail.com", true, 1000,
                LocalDateTime.of(2021, 7, 15, 14, 14, 38),
                LocalDateTime.of(2021, 7, 17, 20, 0, 38));

        reservationService.createReservation("Valerie", "Rayk",
                "vr@gmail.com", true, 1200,
                LocalDateTime.of(2021, 7, 15, 14, 14, 38),
                LocalDateTime.of(2021, 7, 17, 20, 0, 38));

    }

    public  void emulateEmployees() {
        employeeService.createEmployee("Mustafa", "Karawi", "musk@kd.il",
                100, Department.RECEPTION);
        employeeService.createEmployee("Motee", "Abu", "abu@kd.il",
                100, Department.RECEPTION);
    }

    public void emulateRooms() {
        roomService.createRoom(101, 2, 900);
        roomService.createRoom(102, 2, 900);
        roomService.createRoom(103, 2, 1000);
        roomService.createRoom(104, 4, 1150);
        roomService.createRoom(105, 2, 1200);
    }

    void dropTables() {
        String line;
        Session session = HibernateUtils.getHibernateSession();
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
