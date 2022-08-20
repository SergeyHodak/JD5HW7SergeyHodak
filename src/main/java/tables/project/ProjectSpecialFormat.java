package tables.project;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectSpecialFormat {
    private LocalDate creationDate;
    private String name;
    private int developerCount;
}