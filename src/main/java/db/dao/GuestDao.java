package db.dao;

import entity.Guest;

public interface GuestDao {
    Guest getGuestById(long id);

    void persistGuest(Guest guest);

    void update(Guest guest);
}
