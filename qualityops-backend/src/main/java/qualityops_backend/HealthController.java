package qualityops_backend;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        Map<String, Object> summary = new HashMap<>();

        int totalRuns = testRuns.size();
        int totalTests = 0;
        int totalPassed = 0;
        int totalFailed = 0;
        int totalSkipped = 0;

        for (TestRun run : testRuns) {
            totalTests += run.getTotalTests();
            totalPassed += run.getPassed();
            totalFailed += run.getFailed();
            totalSkipped += run.getSkipped();
        }

        double averagePassRate = totalTests > 0 ? ((double) totalPassed / totalTests) * 100 : 0;

        summary.put("totalRuns", totalRuns);
        summary.put("totalTests", totalTests);
        summary.put("totalPassed", totalPassed);
        summary.put("totalFailed", totalFailed);
        summary.put("totalSkipped", totalSkipped);
        summary.put("averagePassRate", averagePassRate);

        return summary;
    }

    @GetMapping("/testruns/project/{projectName}")
    public List<TestRun> getTestRunsByProject(@PathVariable String projectName) {
        List<TestRun> filteredRuns = new ArrayList<>();

        for (TestRun run : testRuns) {
            if (run.getProjectName().equalsIgnoreCase(projectName)) {
                filteredRuns.add(run);
            }
        }

        return filteredRuns;
    }
}