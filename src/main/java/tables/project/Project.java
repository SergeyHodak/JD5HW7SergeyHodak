package tables.project;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tables.company.Company;
import tables.customer.Customer;
import tables.developer.Developer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @NonNull
    private String name;


    @ManyToOne
    @JoinColumn(name = "company_id",
            foreignKey = @ForeignKey(name = "project_ibfk_1")
    )
    @Convert(converter = CompanyConverter.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NonNull
    private Company company;

    @ManyToOne
    @JoinColumn(name = "customer_id",
            foreignKey = @ForeignKey(name = "project_ibfk_2")
    )
    @Convert(converter = CustomerConverter.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NonNull
    private Customer customer;

    @Column(name = "cost")
    private double cost;

    @Column(name = "creation_date")
    @NonNull
    private LocalDate creationDate;

    @JoinTable(
            name = "project_developer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    @ManyToMany(targetEntity = Developer.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Developer> developers = new HashSet<>();

    public Project() {}
}