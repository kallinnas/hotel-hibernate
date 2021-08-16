package service;

import entity.Guest;

public interface GuestService {
    void createGuest(String firstName, String lastName, String email,
                        double account, boolean clubMember);

    boolean isNotExistGuest(String email);

    Guest getGuestByEmail(String email);

    void reduceRoomPriceFromAccount(long id, double price);

    void update(Guest guest);
}
