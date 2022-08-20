package tables.project;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.hibernate.Session;
import storage.HibernateUtil;
import tables.company.Company;

@Converter
public class CompanyConverter implements AttributeConverter<Company, Long> {
    @Override
    public Long convertToDatabaseColumn(Company attribute) {
        return attribute.getId();
    }

    @Override
    public Company convertToEntityAttribute(Long dbData) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.get(Company.class, dbData);
        }
    }
}