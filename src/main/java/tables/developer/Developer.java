package tables.developer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tables.project.Project;
import tables.skill.Skill;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "developer")
public class Developer {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    @NonNull
    private Gender gender;

    public enum Gender {
        MALE,
        FEMALE
    }

    @Column(name = "salary")
    @NonNull
    private double salary;

    @JoinTable(
            name = "developer_skill",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @ManyToMany(targetEntity = Skill.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany(
            targetEntity = Project.class,
            mappedBy = "developers",
            cascade={CascadeType.REMOVE}
    )
    private static Set<Project> projects = new HashSet<>();

    public Developer() {}
}