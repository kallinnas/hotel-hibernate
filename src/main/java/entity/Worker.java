package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.Department;
import model.Request;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "workers")
@EqualsAndHashCode(callSuper = true)
public class Worker extends Client implements Runnable{
//    @Id
//    private long id;
    private Department department;

    /* mappedBy - using name of the class that sets relations between himself and other class.
    *  mappedBy specifically is not allowing to hibernate create additional
    * tables between worker-request entities */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "worker")
    private List<Request> requests = new ArrayList<>();

    @Override
    public void run() {

    }
}
