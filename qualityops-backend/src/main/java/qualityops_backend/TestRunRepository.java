package qualityops_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TestRunRepository extends JpaRepository<TestRun, Long> {
    List<TestRun> findByProjectNameIgnoreCase(String projectName);
    List<TestRun> findByProjectNameIgnoreCaseOrderByIdDesc(String projectName);
    List<TestRun> findAllByOrderByIdAsc();
}