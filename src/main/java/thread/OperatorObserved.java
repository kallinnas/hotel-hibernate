package thread;

import db.dao.RequestDaoImpl;
import entity.Employee;
import lombok.val;
import model.Request;
import model.RoomStatus;
import service.*;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class OperatorObserved {

    /* GENERAL REQUESTS */
    private static Queue<Request> requests = new ArrayDeque<>();

    /* STUFF IN HOTEL */
    private List<Thread> employees = new ArrayList<>();

    private static List<Employee> allEmployees = new ArrayList<>();
    private static List<Employee> availableEmployees = new ArrayList<>();
    private static List<Employee> inWorkEmployees = new ArrayList<>();

    /* SERVICES */
    private static GuestService guestService = new GuestServiceImpl();
    private static EmployeeService employeeService = new EmployeeServiceImpl();
    private static RoomService roomService = new RoomServiceImpl();


    public synchronized static void setEmployeeOnRequest(Employee employee) {
        while (requests.iterator().hasNext()) {
            val request = requests.stream()
                    .filter(r -> r.getType().department.equals(employee.getDepartment()))
                    .findFirst().orElse(null);
            if (request != null) {
                request.setEmployee(employee);
                val res = request.getReservation();
                val room = roomService.getSuitableRoom(res);
                room.setStatus(RoomStatus.OCCUPIED);
                if (guestService.isNotExistGuest(res.getEmail())) {
                    guestService.createGuest(res.getFirstName(), res.getLastName(),
                            res.getEmail(), res.getAccount() - room.getPrice(), res.isClubMember());
                }
                val guest = guestService.getGuestByEmail(res.getEmail());
                guest.setRoom(room);
                res.setRoom(room);
                if (res.isClubMember()) {
                    res.setPrice(room.getPrice() * 0.95);
                } else res.setPrice(room.getPrice());
                res.setGuest(guest);
                res.setInHouse(true);
                double rest = res.getAccount() - res.getPrice();
                if (rest < 0) {
                    res.setExtras(res.getPrice() - res.getAccount());
                    res.setAccount(0);
                } else res.setAccount(rest);
                try {
                    val empPerson = employeeService.getPersonByEmployeeId(employee.getId());
                    val guestPerson = employeeService.getPersonByGuestId(guest.getId());
                    System.out.println(String.format("Employee %s %s start process under %s request for " +
                                    "guest %s %s. It will take %d seconds to finish.",
                            empPerson.getFirstName(), empPerson.getLastName(), request.getType().name,
                            guestPerson.getFirstName(), guestPerson.getLastName(), request.getType().timeLimit / 1000));
                    Thread.sleep(request.getType().timeLimit / 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                val now = LocalDateTime.now();
                request.setCompletionTime(now);
                request.setRoom(room);
                res.setCheckIn(now);
                employee.setRequest(request);
                guestService.update(guest);
                roomService.update(room);
                new ReservationServiceImpl().update(res);
                new RequestDaoImpl().updateRequest(request);
//                OperatorObserved.removeRequest(request);
            }
        }
    }

//    private static void notifyEmployeeAboutRequest() {
//        Request request = requests.poll();
//        Employee employee = getAvailableEmployee(request.getType().department);
//
//    }


//    public static void takeFreeEmployee() {
//        if (employeesFree.size() > 0) {
//            Employee employee = employeesFree.get(0);
//            employeesBusy.add(employee);
//            employeesFree.remove(employee);
//        } else System.out.println("No free employee this moment!");
//    }
//
//    public static void employeeReturns(long employeeId) {
//        for (int i = 0; i < employeesBusy.size(); i++) {
//            if (employeesBusy.get(i).getId() == employeeId) {
//                Employee employee = employeesBusy.get(i);
//                employeesFree.add(employee);
//                employeesBusy.remove(employee);
//            }
//        }
//    }

    public static void addEmployee(Employee employee) {
        allEmployees.add(employee);
    }

    public static void addRequest(Request request) {
        requests.add(request);
//        notifyEmployeeAboutRequest();
    }

//    private static Employee getAvailableEmployee(Department department) {
//        for (Employee e : allEmployees) {
//            e.isAlive();
//        }
//        return null;
//    }

}
