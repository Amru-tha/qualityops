package qualityops_backend;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HealthController {

    private List<TestRun> testRuns = new ArrayList<>();

    @GetMapping("/health")
    public String health() {
        return "QualityOps backend is running";
    }

    @PostMapping("/testruns")
    public String addTestRun(@RequestBody TestRun testRun) {
        testRuns.add(testRun);
        return "Test run added successfully";
    }

    @GetMapping("/testruns")
    public List<TestRun> getTestRuns() {
        return testRuns;
    }
}