package model;

import entity.Guest;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double price;
    private double extras;
}
