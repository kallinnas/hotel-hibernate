package db.dao;

import model.Room;

import java.util.List;

public interface RoomDao {
    List<Room> getVCRoom();

    Room persistRoom(int id, int capacity, double price);

    void update(Room room);
}
