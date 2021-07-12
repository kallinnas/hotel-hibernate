package db.dao;

import db.HibernateUtil;
import db.ValidationUtils;
import entity.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonDaoImpl implements PersonDao {

    @Override
    public void persistPerson(Person person) {
        if (!ValidationUtils.hasViolation(person)) {
            Session session = HibernateUtil.getHibernateSession();
            try {
                Transaction transaction = session.beginTransaction();
                session.persist(person);
                transaction.commit();
            } finally {
                session.close();
            }
        } else ValidationUtils.validate(person);
    }

    @Override
    public Person getPersonById() {
        return null;
    }
}
