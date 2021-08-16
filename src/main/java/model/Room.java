package model;

import entity.Guest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @NotNull
    private int id;

    @NotNull(message = "Must have capacity from 2 to 4!")
    @Size(min = 2, message = "Must have capacity from 2 to 4!")
    private int capacity;

    @NotNull
    private double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Guest> guests = new ArrayList<>();

    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Reservation> reservations;

    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Request> requests;




}
