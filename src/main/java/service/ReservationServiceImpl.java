package service;

import db.dao.RequestDao;
import db.dao.RequestDaoImpl;
import db.dao.ReservationDao;
import db.dao.ReservationDaoImpl;
import model.Request;
import model.RequestType;
import model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private ReservationDao reservationDao = new ReservationDaoImpl();
    private RequestDao requestDao = new RequestDaoImpl();

    @Override
    public Reservation getReservationByEmail(String email) {
        return getReservationsByEmail(email).stream()
                .filter(r -> LocalDateTime.now().isAfter(r.getArrivalTime()))
                .filter(r -> r.getRoom() == null)
                .findFirst().orElse(null);
    }

    @Override
    public List<Reservation> getReservationsByEmail(String email) {
        return reservationDao.getReservation(email);
    }

    @Override
    public void createReservation(String firstName, String lastName, String email,
                                  boolean clubMember, double account,
                                  LocalDateTime arrivalTime, LocalDateTime checkOut) {
        Reservation reservation = Reservation.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .clubMember(clubMember)
                .account(account)
                .arrivalTime(arrivalTime)
                .checkOut(checkOut)
                .build();
        reservationDao.persistReservation(reservation);
        reservation = getReservationByEmail(email);
        requestDao.persistRequest(new Request(RequestType.CHECK_IN, reservation));
    }

    @Override
    public void update(Reservation reservation) {
        reservationDao.updateReservation(reservation);
    }

}
