package tables.project;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.hibernate.Session;
import storage.HibernateUtil;
import tables.customer.Customer;

@Converter
public class CustomerConverter implements AttributeConverter<Customer, Long> {
    @Override
    public Long convertToDatabaseColumn(Customer attribute) {
        return attribute.getId();
    }

    @Override
    public Customer convertToEntityAttribute(Long dbData) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.get(Customer.class, dbData);
        }
    }
}