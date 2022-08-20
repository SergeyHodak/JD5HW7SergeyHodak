package tables.developer;

import jakarta.persistence.*;
import lombok.Data;
import tables.project.Project;
import tables.skill.Skill;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "developer")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "salary")
    private double salary;

    @JoinTable(
            name = "developer_skill",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @ManyToMany(targetEntity = Skill.class)
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany(targetEntity = Project.class, mappedBy = "developers")
    private static Set<Project> projects = new HashSet<>();

    public enum Gender {
        MALE,
        FEMALE
    }
}