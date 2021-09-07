package service;

import db.dao.*;
import entity.Employee;
import entity.Person;
import lombok.SneakyThrows;
import model.Department;
import model.Request;

import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class EmployeeServiceImpl implements EmployeeService {

    private PersonDao personDao;
    private RequestDao requestDao;
    private EmployeeDao employeeDao;
    private static SynchronousQueue<Employee> employees;

    public EmployeeServiceImpl() {
        this.personDao = new PersonDaoImpl();
        this.requestDao = new RequestDaoImpl();
        this.employeeDao = new EmployeeDaoImpl();
    }

    @Override
    public void createEmployee(String firstName, String lastName, String email,
                               double account, Department department) {
        personDao.persistPerson(new Person(firstName, lastName, email, account, department));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Override
    public Person getPersonByEmployeeId(long id) {
        return personDao.getPersonByEmployeeId(id);
    }

    @Override
    public Person getPersonByGuestId(long id) {
        return personDao.getPersonByGuestId(id);
    }

    @SneakyThrows
    @Override
    public synchronized Employee setRequestOnEmployee(Request request) {
        Employee employee = null;
        while (employee == null) {
            employee = employeeDao.getFreeEmployee(request.getType().department);
            if (employee != null) {
                request.setEmployee(employee);
                requestDao.updateRequest(request);
                employee.setRequest(request);
                employee.setWorking(true);
                employeeDao.update(employee);
            } else {
                Thread.currentThread().wait();
            }
        }
        return employee;
    }
//    @SneakyThrows
//    @Override
//    public synchronized Employee setRequestOnEmployee(Request request) {
//        Employee employee = null;
//        while (employee == null) {
//            employee = employeeDao.getFreeEmployee(request.getType().department);
//            if (employee != null) {
//                request.setEmployee(employee);
//                requestDao.updateRequest(request);
//                employee.setRequest(request);
//                employee.setWorking(true);
//                employeeDao.update(employee);
//            } else {
//                Thread.yield();
//            }
//        }
//        return employee;
//    }

    @Override
    public void update(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeDao.getEmployeeById(id);
    }


}
