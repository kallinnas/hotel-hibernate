package db.dao;

import entity.Employee;

public interface EmployeeDao {
    Employee getEmployeeById(long id);
}
