package service;

import db.dao.RoomDao;
import db.dao.RoomDaoImpl;
import lombok.val;
import model.Reservation;
import model.Room;
import model.RoomStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService {

    private RoomDao roomDao = new RoomDaoImpl();

    @Override
    public Room getSuitableRoomForReservation(Reservation reservation) {
        val room = getRoomsByReservationPrice(reservation)
                .stream()
                .reduce((result, current) ->
                        Math.abs(42 - current.getPrice()) > Math.abs(42 - result.getPrice()) ? current : result)
                .orElse(null);
        Objects.requireNonNull(room).setStatus(RoomStatus.OCCUPIED);
        return room;
    }

    @Override
    public Room createRoom(int id, int capacity, double price) {
        return roomDao.persistRoom(id, capacity, price);
    }

    @Override
    public synchronized void update(Room room) {
        roomDao.update(room);
    }

    private List<Room> getRoomsByReservationPrice(Reservation reservation) {
        return roomDao.getVCRoom().stream()
                .filter(room -> reservation.getAccount() - 100 >= room.getPrice() ||
                        reservation.getAccount() + 150 >= room.getPrice())
                .collect(Collectors.toList());
    }
}
