package operator;

import entity.Employee;
import lombok.AllArgsConstructor;
import model.Request;
import positions.Receptionist;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import service.RequestService;
import service.RequestServiceImpl;

import java.util.Queue;
import java.util.concurrent.*;

@AllArgsConstructor
public class OperatorO {
    /* SINGLETON */
    private static OperatorO instance = null;

    /* THREAD EXECUTOR */
    private ExecutorService executorService;
    private Semaphore semaphore;

    /* SERVICES */
    private EmployeeService employeeService;
    private RequestService requestService;

    private Queue<Employee> receptionists;
    private Queue<Request> requests;

    private OperatorO() {
        this.requestService = new RequestServiceImpl();
        this.employeeService = new EmployeeServiceImpl();
        this.executorService = new ThreadPoolExecutor(
                500, 500, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        this.receptionists = employeeService.getAllReceptionists();
        this.requests = requestService.getAllRequests();
    }

    public static OperatorO Operator() {
        if (instance == null) return instance = new OperatorO();
        else return instance;
    }


    public void distributionAndRequestProcessing() {
        System.out.println("***************************************");
        semaphore = new Semaphore(4);
        while (requests.iterator().hasNext()) {
            Request request = requests.poll();
            switch (request.getType().department) {
                case RECEPTION:
                    Employee employee = receptionists.poll();
                    Receptionist receptionist = new Receptionist(employee, semaphore);
                    receptionists.add(employee);
                    employee.setRequest(request);
                    executorService.execute(receptionist);
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


//    @SneakyThrows
//    public void distributionAndRequestProcessing() {
//        Request request = requestService.getTheOldestUndoneRequest();
//        while (request != null) {
//            switch (request.getType().department) {
//                case RECEPTION:
//                    employee = employeeService.setRequestOnEmployee(request);
//                    executorService.execute(employee);
////                    new Thread(employee).start();
//                    request = requestService.getTheOldestUndoneRequest();
//                    break;
//                case HOUSE_KEEPING:
////                    executorService.execute(notifyHouseKeeping(theOldestUndoneRequest));
//                    break;
//                case MAINTENANCE:
////                    executorService.execute(notifyMaintenance(theOldestUndoneRequest));
//                    break;
//                default:
//                    break;
//            }
//        }
//        executorService.shutdown();
//    }

//    public synchronized void receptionistProcesses(Employee employee) {
//        Request request = employee.getRequest();
//
//        Reservation reservation = request.getReservation();
//        Room room = roomService.getSuitableRoomForReservation(reservation);
//        reservation.setRoom(room); // must goes before createAndReturnGuest()
//        val guest = createAndReturnGuest(reservation);
//
//        showCheckInMessage(employee.getId(), guest.getId(), request.getType());
//
//        /* Reservation setters */
//        reservation.setPrice(room.getPrice());
//        reservation.setGuest(guest);
//        reservation.setInHouse(true);
//
//        guest.setRoom(room);
//        request.setRoom(room);
//
//        try {
//            Thread.sleep(request.getType().requiredTimeToCompleteRequest / 4);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        val now = LocalDateTime.now();
//        request.setCompletionTime(now);
//        reservation.setCheckIn(now);
//
//        guestService.update(guest);
//        roomService.update(room);
//        reservationService.update(reservation);
//        requestService.updateRequest(request);
//        employee.setWorking(false);
//        employeeService.update(employee);
////        distributionAndRequestProcessing();
////        Thread.currentThread().notify();
//    }


    private synchronized Employee notifyHouseKeeping(Request request) {
        return null;
    }

    private synchronized Employee notifyMaintenance(Request request) {
        return null;
    }

    public void chambermaidProcesses() {

    }

    public void technicianProcesses() {

    }


}
