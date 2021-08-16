package db.dao;

import db.HQLSchema;
import db.HibernateUtils;
import db.ValidationUtils;
import entity.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.NoSuchElementException;

public class PersonDaoImpl implements PersonDao {

    private Query query;
    private Person person;
    private Session session;
    private Transaction transaction;

    @Override
    public void persistPerson(Person person) {
        if (!ValidationUtils.hasViolation(person)) {
            if (getPersonByEmail(person.getEmail()) == null) {
                session = HibernateUtils.getHibernateSession();
                try {
                    transaction = session.beginTransaction();
                    session.persist(person);
                    transaction.commit();
                } finally {
                    session.close();
                }
            } else System.out.println("Email already exist in DB!");
        } else ValidationUtils.validate(person);
    }

    @Override
    public Person getPersonById(long id) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            return session.get(Person.class, id);
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public boolean isNotExistEmail(String email) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            query = session.createQuery(HQLSchema.SELECT_PERSON_BY_EMAIL);
            query.setParameter("email", email);
            return query.getResultList().isEmpty();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public Person getPersonByEmail(String email) {
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            query = session.createQuery(HQLSchema.SELECT_PERSON_BY_EMAIL);
            query.setParameter("email", email);
            return ((Person) query.getResultList().iterator().next());
        } catch (NoSuchElementException e) {
            return null;
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void updatePersonsAccount(long id, double price) {
        person = getPersonById(id);
        session = HibernateUtils.getHibernateSession();
        try {
            transaction = session.beginTransaction();
            person.setAccount(person.getAccount() - price);
            session.persist(person);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public Person getPersonByEmployeeId(long id) {
        session = HibernateUtils.getHibernateSession();
        try{
            transaction = session.beginTransaction();
            return ((Person)session.createQuery(HQLSchema.SELECT_PERSON_BY_EMPLOYEE_ID)
                    .setParameter("role", 2)
                    .setParameter("client_id", id)
                    .uniqueResult());
        }finally {
            transaction.commit();
            session.close();
        }
    }

    @Override
    public Person getPersonByGuestId(long id) {
        session = HibernateUtils.getHibernateSession();
        try{
            transaction = session.beginTransaction();
            return ((Person)session.createQuery(HQLSchema.SELECT_PERSON_BY_GUEST_ID)
                    .setParameter("role", 1)
                    .setParameter("client_id", id)
                    .uniqueResult());
        }finally {
            transaction.commit();
            session.close();
        }
    }

}
