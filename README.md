# QualityOps

QualityOps is a test analytics and release readiness dashboard built using Java, Spring Boot, H2 Database, HTML, CSS, and JavaScript.
## Dashboard Preview

### Summary Dashboard
![Dashboard Summary](qualityops-backend/screenshots/dashboard-summary.png)

### Project Health Monitoring

![Project Health](qualityops-backend/screenshots/project-health.png)

## Features

- Upload automated test execution reports using JSON
- Calculate pass rate, failed tests, skipped tests, and release readiness
- Dashboard with project health monitoring
- Visual quality comparison chart
- REST APIs for test management and reporting
- Automatic project status classification:
  - HEALTHY
  - WARNING
  - CRITICAL

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- HTML
- CSS
- JavaScript
- Chart.js
- Maven
- GitHub

## API Endpoints

### Health Check

GET

```
/health
```

### Upload Test Report

POST

```
/upload-report
```

### Get Dashboard Data

GET

```
/dashboard
```

### Get All Test Runs

GET

```
/testruns
```

### Get Project Readiness

GET

```
/readiness/{projectName}
```

## Sample Report

```json
{
  "projectName": "LoginApp",
  "buildNumber": "402",
  "tests": [
    {
      "testName": "validLoginTest",
      "status": "PASS"
    },
    {
      "testName": "logoutTest",
      "status": "PASS"
    }
  ]
}
```


## Project Goal

This project simulates a Quality Engineering dashboard used by QA teams to monitor automated test execution results, analyze project quality trends, and evaluate release readiness before production deployment.
## Business Value

QualityOps helps QA teams:

- Monitor automated test execution results
- Evaluate release readiness
- Track project quality trends
- Identify high-risk releases before deployment
- Improve visibility for QA managers and stakeholders
## Future Enhancements

- User authentication and role management
- Historical trend analysis
- Email notifications for failed builds
- CI/CD integration with Jenkins and GitHub Actions
- Export dashboard reports to PDF
## Key Skills Demonstrated

- Java Programming
- Spring Boot Development
- REST API Design
- JSON Data Processing
- Test Analytics and Reporting
- Release Readiness Evaluation
- Frontend Development (HTML, CSS, JavaScript)
- Data Visualization with Chart.js
- Git and GitHub Version Control
- Quality Engineering Concepts
## Author

Amrutha Deeti

Master's Student – Information Technology Management  
QA Automation Engineer | Java | Selenium | Spring Boot | SQL


