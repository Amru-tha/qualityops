package qualityops_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final TestRunRepository repository;

    public DataLoader(TestRunRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new TestRun("LoginApp", "402", 120, 118, 2, 0));
            repository.save(new TestRun("PaymentsApp", "300", 80, 70, 10, 0));
            repository.save(new TestRun("OrdersApp", "210", 95, 91, 3, 1));
        }
    }
}