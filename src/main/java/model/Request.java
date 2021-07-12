package model;

import entity.Employee;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Transient
    private RequestType type;

    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Room room;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private final LocalDateTime start = LocalDateTime.now();
    private LocalDateTime end;


}
