package tables.skill;

import jakarta.persistence.*;
import lombok.Data;
import tables.developer.Developer;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "department")
    private String department;

    @Column(name = "skill_level")
    private String skillLevel;

    @ManyToMany(targetEntity = Developer.class, mappedBy = "skills")
    private static Set<Developer> developers = new HashSet<>();
}