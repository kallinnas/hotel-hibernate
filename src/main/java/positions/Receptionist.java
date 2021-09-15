package positions;

import entity.Employee;
import entity.Guest;
import lombok.val;
import model.Request;
import model.RequestType;
import model.Reservation;
import model.Room;
import service.*;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

public class Receptionist implements Runnable {

    private Room room;
    private Request request;
    private Employee employee;
    private Reservation reservation;
    private Semaphore semaphore;
    /* SERVICES */
    private ReservationService reservationService;
    private EmployeeService employeeService;
    private RequestService requestService;
    private GuestService guestService;
    private RoomService roomService;

    public Receptionist(Employee employee, Semaphore semaphore) {
        this.employee = employee;
        this.roomService = new RoomServiceImpl();
        this.guestService = new GuestServiceImpl();
        this.requestService = new RequestServiceImpl();
        this.employeeService = new EmployeeServiceImpl();
        this.reservationService = new ReservationServiceImpl();
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            this.semaphore.acquire();
            System.out.println("RUN " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        request = employee.getRequest();

        reservation = request.getReservation();
        room = roomService.getSuitableRoomForReservation(reservation);
        reservation.setRoom(room); // must goes before createAndReturnGuest()
        val guest = createAndReturnGuest(reservation);

        showCheckInMessage(employee.getId(), guest.getId(), request.getType());

        /* Reservation setters */
        reservation.setPrice(room.getPrice());
        reservation.setGuest(guest);
        reservation.setInHouse(true);

        guest.setRoom(room);
        request.setRoom(room);

        try {
            Thread.sleep(request.getType().requiredTimeToCompleteRequest / 4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        val now = LocalDateTime.now();
        request.setCompletionTime(now);
        reservation.setCheckIn(now);

        guestService.update(guest); //
        roomService.update(room);
        reservationService.update(reservation);
        requestService.updateRequest(request);
        employee.setWorking(false);
        employeeService.update(employee);

        semaphore.release();
    }

    private Guest createAndReturnGuest(Reservation r) {
        if (guestService.isNotExistGuest(r.getEmail())) {
            guestService.createGuest(r.getFirstName(), r.getLastName(), r.getEmail(),
                    r.getAccount() - r.getRoom().getPrice(), r.isClubMember());
        }
        return guestService.getGuestByEmail(r.getEmail());
    }

    private void showCheckInMessage(long employeeId, long guestId, RequestType requestType) {
        val empPerson = employeeService.getPersonByEmployeeId(employeeId);
        val guestPerson = employeeService.getPersonByGuestId(guestId);
        System.out.println(String.format("Employee %s %s start process under %s request for " +
                        "guest %s %s. It will take %d seconds to finish.",
                empPerson.getFirstName(), empPerson.getLastName(), requestType.name,
                guestPerson.getFirstName(), guestPerson.getLastName(), requestType.requiredTimeToCompleteRequest / 1000));
    }

}
