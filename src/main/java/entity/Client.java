package entity;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public static final long NO_ID = -1;
}
