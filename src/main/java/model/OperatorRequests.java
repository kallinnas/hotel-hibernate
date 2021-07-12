package model;

import entity.Employee;

import java.util.*;

public class OperatorRequests implements RequestObserved {

    public static List<Request> requests = new ArrayList<>();

    private List<Employee> employees = new ArrayList<>();

    public static void addRequest(Request request) {
        requests.add(request);
    }

    public static void removeRequest(Request request) {
        requests.remove(request);
    }

    @Override
    public void addObserver(Observer observer) {
        employees.add((Employee) observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        employees.remove(observer);
    }

    @Override
    public void findExecutor() {
        Employee executor = null;
        Optional<Employee> optional = employees.stream()
                .filter(e -> !(new Thread(e).getState().toString().equals("TIMED_WAITING")))
                .max(Comparator.comparing(Employee::getLastUpdated));
        if (optional.isPresent()) executor = optional.get();


    }
}
