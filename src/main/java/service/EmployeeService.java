package service;

import entity.Employee;
import entity.Person;
import model.Department;
import model.Request;

import java.util.List;
import java.util.Queue;

public interface EmployeeService {

    void createEmployee(String firstName, String lastName, String email,
                        double account, Department department);

    List<Employee> getAllEmployees();

    Person getPersonByEmployeeId(long id);

    Person getPersonByGuestId(long id);

    Employee setRequestOnEmployee(Request request);

    void update(Employee employee);

    Employee getEmployeeById(long id);

    Queue<Employee> getAllReceptionists();

    int getAmountOfReceptionists();
}
