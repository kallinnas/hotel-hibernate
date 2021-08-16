package db.dao;

import entity.Person;

public interface PersonDao {
    void persistPerson(Person person);

    Person getPersonById(long id);

    boolean isNotExistEmail(String email);

    Person getPersonByEmail(String email);

    void updatePersonsAccount(long id, double price);

    Person getPersonByEmployeeId(long id);

    Person getPersonByGuestId(long id);
}
