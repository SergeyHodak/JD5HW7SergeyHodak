package tables.skill;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tables.developer.Developer;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "department")
    @NonNull
    private String department;

    @Column(name = "skill_level")
    @NonNull
    private String skillLevel;

    @ManyToMany(
            targetEntity = Developer.class,
            mappedBy = "skills",
            cascade={CascadeType.REMOVE}
    )
    private static Set<Developer> developers = new HashSet<>();

    public Skill() {}
}