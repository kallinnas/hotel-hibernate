package model;

import entity.Guest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    private int id;

    @NotNull(message = "Must have capacity from 2 to 4!")
    @Size(min = 2, message = "Must have capacity from 2 to 4!")
    private int capacity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Guest> guests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Reservation> reservations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Request> requests;




}
