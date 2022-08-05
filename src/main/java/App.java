import storage.DatabaseInitService;
import storage.StorageConstants;

public class App {
    public static void main(String[] args) {
        new DatabaseInitService().initDb(
                StorageConstants.DB_URL,
                StorageConstants.DB_USERNAME,
                StorageConstants.DB_PASSWORD
        );
    }
}