package db.dao;

import db.HibernateUtil;
import entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeDaoImpl implements EmployeeDao {
    private Employee employee;

    @Override
    public Employee getEmployeeById(long id) {
        Session session = HibernateUtil.getHibernateSession();
        try {
            Transaction transaction = session.beginTransaction();
            employee = session.get(Employee.class, id);
            transaction.commit();
        } finally {
            session.close();
        }
        return employee;
    }
}
