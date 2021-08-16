package service;

import db.dao.GuestDao;
import db.dao.GuestDaoImpl;
import db.dao.PersonDao;
import db.dao.PersonDaoImpl;
import entity.Guest;
import entity.Person;

public class GuestServiceImpl implements GuestService {

    private PersonDao personDao = new PersonDaoImpl();
    private GuestDao guestDao = new GuestDaoImpl();

    @Override
    public void createGuest(String firstName, String lastName, String email,
                            double account, boolean clubMember) {
        Person person = new Person(firstName, lastName, email, account, clubMember);
        personDao.persistPerson(person);
    }

    @Override
    public boolean isNotExistGuest(String email) {
        return personDao.isNotExistEmail(email);
    }

    @Override
    public Guest getGuestByEmail(String email) {
        return guestDao.getGuestById(personDao.getPersonByEmail(email).getClient().getId());
    }

    @Override
    public void reduceRoomPriceFromAccount(long id, double price) {
        personDao.updatePersonsAccount(id, price);
    }

    @Override
    public void update(Guest guest) {
        guestDao.update(guest);
    }


}
