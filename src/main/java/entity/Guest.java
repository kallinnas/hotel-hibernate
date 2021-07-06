package entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "guests")
public class Guest extends Client {

    /* provides one id arg constructor for method empty
    that used in services to return not found(non-exist) entity */
    @NonNull
    private long id;
    private boolean clubMember;
    private ArrayList<Reservation> reservations;

//    public static Guest empty() {
//        return new Guest(NO_ID);
//    }
}
