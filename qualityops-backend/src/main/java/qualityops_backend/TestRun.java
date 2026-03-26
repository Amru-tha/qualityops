package qualityops_backend;

public class TestRun {

    private String projectName;
    private String buildNumber;
    private int totalTests;
    private int passed;
    private int failed;
    private int skipped;

    public TestRun() {
    }

    public TestRun(String projectName, String buildNumber, int totalTests, int passed, int failed, int skipped) {
        this.projectName = projectName;
        this.buildNumber = buildNumber;
        this.totalTests = totalTests;
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
    }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getBuildNumber() { return buildNumber; }
    public void setBuildNumber(String buildNumber) { this.buildNumber = buildNumber; }

    public int getTotalTests() { return totalTests; }
    public void setTotalTests(int totalTests) { this.totalTests = totalTests; }

    public int getPassed() { return passed; }
    public void setPassed(int passed) { this.passed = passed; }

    public int getFailed() { return failed; }
    public void setFailed(int failed) { this.failed = failed; }

    public int getSkipped() { return skipped; }
    public void setSkipped(int skipped) { this.skipped = skipped; }
}