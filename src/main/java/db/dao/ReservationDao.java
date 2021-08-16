package db.dao;

import model.Reservation;

import java.util.List;

public interface ReservationDao {

    void persistReservation(Reservation reservation);

    List<Reservation> getReservation(String email);

    void updateReservation(Reservation reservation);
}
