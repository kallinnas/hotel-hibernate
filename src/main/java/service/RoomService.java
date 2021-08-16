package service;

import model.Reservation;
import model.Room;

public interface RoomService {
    Room getSuitableRoom(Reservation reservation);

    Room createRoom(int id, int capacity, double price);

    void update(Room room);
}
