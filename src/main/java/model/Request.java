package model;

import entity.Employee;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @NonNull
    @Enumerated(EnumType.STRING)
    private RequestType type;

    @NonNull
    @ToString.Exclude
    @JoinColumn(name = "reservation_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Reservation reservation;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Room room;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private final LocalDateTime start = LocalDateTime.now();
    @Column(name = "completion_time")
    private LocalDateTime completionTime;

}
