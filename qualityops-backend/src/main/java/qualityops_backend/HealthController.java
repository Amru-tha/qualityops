package qualityops_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired
    private TestRunRepository repository;

    @GetMapping("/health")
    public String health() {
        return "QualityOps backend is running";
    }

    @PostMapping("/testruns")
    public TestRun addTestRun(@RequestBody TestRun testRun) {
        return repository.save(testRun);
    }

    @GetMapping("/testruns")
    public List<TestRun> getTestRuns() {
        return repository.findAll();
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        List<TestRun> testRuns = repository.findAll();

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
        return repository.findByProjectNameIgnoreCase(projectName);
    }

    @GetMapping("/readiness/{projectName}")
    public Map<String, Object> getReleaseReadiness(@PathVariable String projectName) {
        List<TestRun> projectRuns = repository.findByProjectNameIgnoreCase(projectName);

        Map<String, Object> readiness = new HashMap<>();

        if (projectRuns.isEmpty()) {
            readiness.put("projectName", projectName);
            readiness.put("status", "NO_DATA");
            readiness.put("message", "No test runs found for this project");
            return readiness;
        }

        TestRun latestRun = projectRuns.get(projectRuns.size() - 1);

        double passRate = latestRun.getTotalTests() > 0
                ? ((double) latestRun.getPassed() / latestRun.getTotalTests()) * 100
                : 0;

        String status;
        if (passRate >= 95) {
            status = "HEALTHY";
        } else if (passRate >= 85) {
            status = "WARNING";
        } else {
            status = "CRITICAL";
        }

        readiness.put("projectName", latestRun.getProjectName());
        readiness.put("buildNumber", latestRun.getBuildNumber());
        readiness.put("passRate", passRate);
        readiness.put("failed", latestRun.getFailed());
        readiness.put("status", status);

        return readiness;
    }

    @GetMapping("/testruns/latest/{projectName}")
    public Map<String, Object> getLatest(@PathVariable String projectName) {
        List<TestRun> runs = repository.findByProjectNameIgnoreCaseOrderByIdDesc(projectName);

        if (runs.isEmpty()) {
            return null;
        }

        TestRun latest = runs.get(0);
        double passRate = latest.getTotalTests() == 0 ? 0
                : (latest.getPassed() * 100.0) / latest.getTotalTests();

        Map<String, Object> response = new HashMap<>();
        response.put("projectName", latest.getProjectName());
        response.put("buildNumber", latest.getBuildNumber());
        response.put("totalTests", latest.getTotalTests());
        response.put("passed", latest.getPassed());
        response.put("failed", latest.getFailed());
        response.put("skipped", latest.getSkipped());
        response.put("passRate", passRate);

        return response;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        List<TestRun> allRuns = repository.findAllByOrderByIdAsc();

        Map<String, Object> response = new HashMap<>();

        int totalRuns = allRuns.size();
        int totalTests = 0;
        int totalPassed = 0;
        int totalFailed = 0;
        int totalSkipped = 0;

        Map<String, Map<String, Object>> latestProjects = new HashMap<>();

        for (TestRun run : allRuns) {
            totalTests += run.getTotalTests();
            totalPassed += run.getPassed();
            totalFailed += run.getFailed();
            totalSkipped += run.getSkipped();

            Map<String, Object> project = new HashMap<>();
            project.put("projectName", run.getProjectName());
            project.put("buildNumber", run.getBuildNumber());
            project.put("totalTests", run.getTotalTests());
            project.put("passed", run.getPassed());
            project.put("failed", run.getFailed());
            project.put("skipped", run.getSkipped());

            double passRate = run.getTotalTests() == 0 ? 0
                    : (run.getPassed() * 100.0) / run.getTotalTests();

            String status;
            if (passRate >= 95) {
                status = "HEALTHY";
            } else if (passRate >= 85) {
                status = "WARNING";
            } else {
                status = "CRITICAL";
            }

            project.put("passRate", passRate);
            project.put("status", status);

            latestProjects.put(run.getProjectName(), project);
        }

        double averagePassRate = totalTests == 0 ? 0
                : (totalPassed * 100.0) / totalTests;

        String overallStatus;
        if (averagePassRate >= 95) {
            overallStatus = "HEALTHY";
        } else if (averagePassRate >= 85) {
            overallStatus = "WARNING";
        } else {
            overallStatus = "CRITICAL";
        }

        response.put("overallStatus", overallStatus);
        response.put("totalRuns", totalRuns);
        response.put("totalTests", totalTests);
        response.put("totalPassed", totalPassed);
        response.put("totalFailed", totalFailed);
        response.put("totalSkipped", totalSkipped);
        response.put("averagePassRate", averagePassRate);
        response.put("projects", latestProjects.values());

        return response;
    }
    @PostMapping("/upload-report")
        public TestRun uploadReport(@RequestBody Map<String, Object> report) {

            String projectName = (String) report.get("projectName");
            String buildNumber = (String) report.get("buildNumber");

            List<Map<String, String>> tests = (List<Map<String, String>>) report.get("tests");

                int totalTests = tests.size();
                int passed = 0;
                int failed = 0;
                int skipped = 0;

                for (Map<String, String> test : tests) {
                    String status = test.get("status");

                    if ("PASS".equalsIgnoreCase(status)) {
                        passed++;
                    } else if ("FAIL".equalsIgnoreCase(status)) {
                        failed++;
                    } else if ("SKIP".equalsIgnoreCase(status) || "SKIPPED".equalsIgnoreCase(status)) {
                        skipped++;
                    }
                }

                TestRun run = new TestRun();
                run.setProjectName(projectName);
                run.setBuildNumber(buildNumber);
                run.setTotalTests(totalTests);
                run.setPassed(passed);
                run.setFailed(failed);
                run.setSkipped(skipped);

                return repository.save(run);
        }
}