package tables.customer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    @NonNull
    private String firstName;

    @Column(name = "second_name")
    @NonNull
    private String secondName;

    @Column(name = "age")
    @NonNull
    private int age;

    public Customer() {}
}