package entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import model.Department;
import model.Request;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "employees")
@EqualsAndHashCode(callSuper = true)
public class Employee extends Client {

    private long id;
    @NotNull(message = "Department must be set!")
    @Enumerated(EnumType.STRING)
    private Department department;

    @NonNull
    private boolean isWorking;

    @Transient //Every non-static, non-final entity field is persistent by default in Hibernate or JPA
    private Request request;

    /* mappedBy - using name of the class that sets relations between himself and other class.
     *  mappedBy specifically is not allowing to hibernate create additional
     * tables between worker-request entities */
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee"/*, fetch = FetchType.LAZY*/)
    private List<Request> requests = new ArrayList<>();

    public Employee(long id) {
        super(id);
    }


}
