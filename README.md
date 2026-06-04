# QualityOps

QualityOps is a test analytics and release readiness dashboard built using Java, Spring Boot, H2 Database, HTML, CSS, and JavaScript.

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

This project simulates a Quality Engineering dashboard used by QA teams to monitor automated test execution results and evaluate release readiness before production deployment.

## Author

Amrutha Deeti
