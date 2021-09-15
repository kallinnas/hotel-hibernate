package db.dao;

import db.HQLSchema;
import db.HibernateUtils;
import entity.Employee;
import model.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    private Session session;
    private Transaction transaction;

    @Override
    public Employee getEmployeeById(long id) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            return session.get(Employee.class, id);
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void update(Employee employee) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            session.update(employee);
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void persistEmployee(Employee employee) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            session.persist(employee);
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            List list = session.createQuery(HQLSchema.SELECT_ALL_EMPLOYEES).getResultList();
            return new ArrayList<Employee>(list);
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public Employee getFreeEmployee(Department department) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            return session.createQuery(HQLSchema.SELECT_FREE_EMPLOYEE, Employee.class)
                    .setMaxResults(1).uniqueResult();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public int getAmountOfReceptionists() {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            return  session.createQuery(HQLSchema.SELECT_AMOUNT_OF_RECEPTIONISTS)
                    .setParameter("department", Department.RECEPTION).getFirstResult();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Employee> getAllReceptionists() {
        session = HibernateUtils.getHibernateSession();
        try{
            transaction = session.beginTransaction();
            List list = session.createQuery(HQLSchema.SELECT_ALL_RECEPTIONISTS)
                    .setParameter("receptionists", Department.RECEPTION)
                    .getResultList();
            return new ArrayList<Employee>(list);
        }finally {
            transaction.commit();
            session.close();
        }
    }


}
