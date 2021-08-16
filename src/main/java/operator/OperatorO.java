package operator;

import entity.Employee;
import entity.Guest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import model.Request;
import model.Reservation;
import model.Room;
import service.*;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class OperatorO {
    /* SINGLETON */
    private static OperatorO instance = null;

    /* THREAD EXECUTOR */
    private ExecutorService executorService;

    /* SERVICES */
    private ReservationService reservationService;
    private EmployeeService employeeService;
    private RequestService requestService;
    private GuestService guestService;
    private RoomService roomService;

    public static OperatorO Operator() {
        if (instance == null) return instance = new OperatorO();
        else return instance;
    }

    private OperatorO() {
        this.roomService = new RoomServiceImpl();
        this.guestService = new GuestServiceImpl();
        this.requestService = new RequestServiceImpl();
        this.employeeService = new EmployeeServiceImpl();
        this.reservationService = new ReservationServiceImpl();
        this.executorService = new ThreadPoolExecutor(
                6, 6, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    @SneakyThrows
    public synchronized void processRequests() {
        Request request = requestService.getTheOldestUndoneRequest();
        while (request != null) {
            switch (request.getType().department) {
                case RECEPTION:
                    executorService.execute(employeeService.setRequestOnEmployee(request));
                    request = requestService.getTheOldestUndoneRequest();
                    break;
                case HOUSE_KEEPING:
                    executorService.execute(notifyHouseKeeping(request));
                    break;
                case MAINTENANCE:
                    executorService.execute(notifyMaintenance(request));
                    break;
                default:
                    break;
            }

        }
        executorService.shutdown();
    }

    private synchronized Employee notifyHouseKeeping(Request request) {
        return null;
    }

    private synchronized Employee notifyMaintenance(Request request) {
        return null;
    }


    public synchronized void receptionistProcesses(Employee employee) {
        Request request = employee.getRequest();
        Reservation reservation = request.getReservation();
        Room room = roomService.getSuitableRoom(reservation);
        reservation.setRoom(room); // must goes before createAndReturnGuest()
        val guest = createAndReturnGuest(reservation);

        val empPerson = employeeService.getPersonByEmployeeId(employee.getId());
        val guestPerson = employeeService.getPersonByGuestId(guest.getId());

        /* Reservation setters */
        reservation.setPrice(room.getPrice());
        reservation.setGuest(guest);
        reservation.setInHouse(true);

        guest.setRoom(room);
        request.setRoom(room);

        System.out.println(String.format("Employee %s %s start process under %s request for " +
                        "guest %s %s. It will take %d seconds to finish.",
                empPerson.getFirstName(), empPerson.getLastName(), request.getType().name,
                guestPerson.getFirstName(), guestPerson.getLastName(), request.getType().timeLimit / 1000));
        try {
            Thread.sleep(request.getType().timeLimit / 4);
            System.out.println(Thread.activeCount());
            System.out.println(Thread.currentThread().getName());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        val now = LocalDateTime.now();
        request.setEmployee(employee);
        request.setCompletionTime(now);
        reservation.setCheckIn(now);

        employee.setRequest(request);
        guestService.update(guest);
        roomService.update(room);
        reservationService.update(reservation);
        requestService.updateRequest(request);
        employee.setWorking(false);
        employeeService.update(employee);
        processRequests();
    }


    public void chambermaidProcesses() {

    }

    public void technicianProcesses() {

    }

    private Guest createAndReturnGuest(Reservation r) {
        if (guestService.isNotExistGuest(r.getEmail())) {
            guestService.createGuest(r.getFirstName(), r.getLastName(), r.getEmail(),
                    r.getAccount() - r.getRoom().getPrice(), r.isClubMember());
        }
        return guestService.getGuestByEmail(r.getEmail());
    }


}
