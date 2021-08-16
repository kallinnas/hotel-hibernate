package service;

import model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    Reservation getReservationByEmail(String email);

    List getReservationsByEmail(String email);

    void createReservation(String firstName, String lastName, String email,
                           boolean clubMember, double account,
                           LocalDateTime arrivalTime, LocalDateTime checkOut);


    void update(Reservation reservation);
}
