package operator;

import entity.Employee;
import entity.Guest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import model.Request;
import model.RequestType;
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
    private volatile static OperatorO instance = null;

    /* THREAD EXECUTOR */
    private volatile ExecutorService executorService;

    /* SERVICES */
    private volatile ReservationService reservationService;
    private volatile EmployeeService employeeService;
    private volatile RequestService requestService;
    private volatile GuestService guestService;
    private volatile RoomService roomService;

//    private List<Request> requests;
    private volatile Employee employee;

    private OperatorO() {
        this.roomService = new RoomServiceImpl();
        this.guestService = new GuestServiceImpl();
        this.requestService = new RequestServiceImpl();
        this.employeeService = new EmployeeServiceImpl();
        this.reservationService = new ReservationServiceImpl();
        this.executorService = new ThreadPoolExecutor(
                10, 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    public static OperatorO Operator() {
        if (instance == null) return instance = new OperatorO();
        else return instance;
    }

    @SneakyThrows
    public synchronized void distributionAndRequestProcessing() {
        Request request = requestService.getTheOldestUndoneRequest();
        while (request != null) {
            switch (request.getType().department) {
                case RECEPTION:
                    employee = employeeService.setRequestOnEmployee(request);
                    executorService.execute(employee);
                    request = requestService.getTheOldestUndoneRequest();
                    break;
                case HOUSE_KEEPING:
//                    executorService.execute(notifyHouseKeeping(theOldestUndoneRequest));
                    break;
                case MAINTENANCE:
//                    executorService.execute(notifyMaintenance(theOldestUndoneRequest));
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
        Room room = roomService.getSuitableRoomForReservation(reservation);
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

//        employee.setRequest(request);
        guestService.update(guest);
        roomService.update(room);
        reservationService.update(reservation);
        requestService.updateRequest(request);
        employee.setWorking(false);
        employeeService.update(employee);
//        distributionAndRequestProcessing();
    }

    private void showCheckInMessage(long employeeId, long guestId, RequestType requestType) {
        val empPerson = employeeService.getPersonByEmployeeId(employeeId);
        val guestPerson = employeeService.getPersonByGuestId(guestId);
        System.out.println(String.format("Employee %s %s start process under %s request for " +
                        "guest %s %s. It will take %d seconds to finish.",
                empPerson.getFirstName(), empPerson.getLastName(), requestType.name,
                guestPerson.getFirstName(), guestPerson.getLastName(), requestType.requiredTimeToCompleteRequest / 1000));
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
