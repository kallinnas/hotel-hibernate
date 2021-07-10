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
//@RequiredArgsConstructor
@Table(name = "guests")
@EqualsAndHashCode(callSuper = true)
public class Guest extends Client {

//    /* provides one id arg constructor for method empty
//    that used in services to return not found(non-exist) entity */
//    @NonNull
//    private long id;
    private boolean clubMember;

    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guest")
    private List<Reservation> reservations = new ArrayList<>();



}
