package db.dao;

import entity.Person;

public interface PersonDao {
    void persistPerson(Person person);

    Person getPersonById();


}
