package entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

@Data
@Entity
@Table(name = "persons")
@NoArgsConstructor
public class Person     {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private double account;

    @Any(metaColumn = @Column(name = "role"))
    @AnyMetaDef(idType = "long", metaType = "int",
            metaValues = {
                    @MetaValue(value = "1", targetEntity = Guest.class),
                    @MetaValue(value = "2", targetEntity = Worker.class),
            })
    @JoinColumn(name = "client_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Client client;

    public Person(String firstName, String lastName,
                  String email, double account, int role) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.account = account;
        if (role == 1) {
            this.client = new Guest();
            this.client.setId(0);
        } else if (role == 2) {
            this.client = new Worker();
            this.client.setId(0);
        }
    }

}
