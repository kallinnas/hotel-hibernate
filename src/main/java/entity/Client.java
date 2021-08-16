package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@MappedSuperclass
@NoArgsConstructor
public abstract class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public static final long NO_ID = -1;

    public Client(long id) {
        this.id = id;
    }
}
