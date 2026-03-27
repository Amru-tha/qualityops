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
    @GetMapping("/readiness/{projectName}")
    public Map<String, Object> getReleaseReadiness(@PathVariable String projectName) {
        Map<String, Object> readiness = new HashMap<>();

        TestRun latestRun = null;

        for (int i = testRuns.size() - 1; i >= 0; i--) {
            TestRun run = testRuns.get(i);
            if (run.getProjectName().equalsIgnoreCase(projectName)) {
                latestRun = run;
                break;
            }
        }

        if (latestRun == null) {
            readiness.put("projectName", projectName);
            readiness.put("status", "NO_DATA");
            readiness.put("message", "No test runs found for this project");
            return readiness;
        }

        double passRate = latestRun.getTotalTests() > 0
            ? ((double) latestRun.getPassed() / latestRun.getTotalTests()) * 100
            : 0;

        String status;
        if (passRate >= 95 && latestRun.getFailed() == 0) {
        status = "READY";
        } else if (passRate >= 85) {
            status = "WARNING";
        } else {
            status = "NOT_READY";
        }

        readiness.put("projectName", latestRun.getProjectName());
        readiness.put("buildNumber", latestRun.getBuildNumber());
        readiness.put("passRate", passRate);
        readiness.put("failed", latestRun.getFailed());
        readiness.put("status", status);

        return readiness;
    }
}