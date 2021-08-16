package entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Department;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;


@Data
@Entity
@Table(name = "persons")
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Name must be given!")
    @Column(name = "first_name")
    private String firstName;
    @NotNull(message = "Family name must be given!")
    @Size(min = 2, message = "Family name must have at least 2 letters!")
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$",
            message = "Given email can not exist!")
    @NotNull(message = "Email must be set!")
    private String email;

    private double account;

    @Any(metaColumn = @Column(name = "role"))
    @AnyMetaDef(idType = "long", metaType = "int",
            metaValues = {
                    @MetaValue(value = "1", targetEntity = Guest.class),
                    @MetaValue(value = "2", targetEntity = Employee.class),
            })
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    public Person(String firstName, String lastName, String email,
                  double account, Department department) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.account = account;
        this.client = new Employee();
        this.client.setId(this.id);
        ((Employee)this.client).setDepartment(department);
    }

    public Person(String firstName, String lastName, String email,
                  double account, boolean clubMember) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.account = account;
        this.client = new Guest();
        this.client.setId(this.id);
        ((Guest)this.client).setClubMember(clubMember);
    }

}
