package db.dao;

import entity.Employee;
import model.Department;

import java.util.List;

public interface EmployeeDao {
    Employee getEmployeeById(long id);

    void update(Employee employee);

    void persistEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getFreeEmployee(Department department);

}
