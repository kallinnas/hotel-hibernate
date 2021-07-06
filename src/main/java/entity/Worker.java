package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "workers")
@NoArgsConstructor
public class Worker extends Client {
    @Id
    private long id;
    private Department department;
    private ArrayList<Call> calls;
}
