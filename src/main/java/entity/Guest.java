package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.Reservation;
import model.Room;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "guests")
@EqualsAndHashCode(callSuper = true)
public class Guest extends Client {

    private long id;
    private boolean clubMember;

    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guest"/*, fetch = FetchType.LAZY*/)
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guest", fetch = FetchType.EAGER)
    private List<Reservation> reservations = new ArrayList<>();

}
