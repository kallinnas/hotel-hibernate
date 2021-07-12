package entity;

import db.dao.RequestDaoImpl;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.Department;
import model.OperatorRequests;
import model.Request;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "employees")
@EqualsAndHashCode(callSuper = true)
public class Employee extends Client implements RequestObserver, Runnable {

    @NotNull(message = "Department must be set!")
    private Department department;

    @Transient //Every non-static, non-final entity field is persistent by default in Hibernate or JPA
    private volatile Request request;

    /* mappedBy - using name of the class that sets relations between himself and other class.
     *  mappedBy specifically is not allowing to hibernate create additional
     * tables between worker-request entities */
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.EAGER)
    private List<Request> requests = new ArrayList<>();

    @Override
//    @SneakyThrows
    public void run() {
        while (!OperatorRequests.requests.isEmpty()) {
            request = OperatorRequests.requests.get(0);
            handleRequest();
            System.out.println(request + " was made by employee id#" + getId());
            System.out.println(LocalDateTime.now() + "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            try {
                Thread.sleep(request.getType().timeLimit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now() + "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            request.setEnd(LocalDateTime.now());
            System.out.println(request);
            requests.add(request);
            new RequestDaoImpl().updateRequest(request);
            OperatorRequests.removeRequest(request);
        }
    }

    @Override
    public void handleRequest() {
        request.setEmployee(this);
    }

    public LocalDateTime getLastUpdated() {
        return requests.get(requests.size() - 1).getEnd();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "department=" + department +
                '}';
    }
}
