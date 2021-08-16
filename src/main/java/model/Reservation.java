package model;

import entity.Guest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "findReservationByEmail",
                query = "FROM Reservation r WHERE r.email= :email")
}
)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /* GUEST PROPERTIES FOR CREATION PERSON IN DB */
    @NotNull(message = "Name must be given!")
    private String firstName;
    @NotNull(message = "Family name must be given!")
    @Size(min = 2, message = "Family name must have at least 2 letters!")
    private String lastName;
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$",
            message = "Given email can not exist!")
    @NotNull(message = "Email must be set!")
    private String email;
    @NonNull
    private boolean clubMember;
    @NonNull
    private double account;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    @PrimaryKeyJoinColumn
    private Guest guest;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation", fetch = FetchType.EAGER)
    private List<Request> requests;

    private boolean inHouse;
    @NonNull
    private LocalDateTime arrivalTime;
    private LocalDateTime checkIn;
    @NonNull
    private LocalDateTime checkOut;
    private double price;
    private double extras;

    public void setPrice(double price) {
        if (isClubMember()) this.price = price * 0.95;
        else this.price = price;
    }
}
