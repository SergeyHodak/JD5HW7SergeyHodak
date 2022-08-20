package web.command.project;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Format {
    private long id;
    private String name;
    private long companyId;
    private long customerId;
    private double cost;
    private LocalDate creationDate;
    private List<Long> developers;
}
