# AIS Supplementary Assessment.

## Overview

This project is a multi-module Java application designed to process text files. The 
`core-processor` module contains the main file processing code, which is then consumed by the other
two modules to demonstrate its use in different contexts, namely as a Command-Line Interface (CLI)
application as well as a Spring Boot RESTful web application.

---

## Getting Started

### Prerequisites

- Java 21 (JDK)
- Maven
- Docker (optional, for containerised deployment)

---

### Build Instructions

From the project root, build all JAR files by executing:

```bash
mvn clean install
```

### CLI Demonstration Application

Here is the usage for the CLI demonstration application:
```
Usage: java -jar app-cli/target/app-cli-1.0.0.jar [options]
        
Options:
  -i, --in-file             Input text file. Required. Absolute path
  -o, --out-file            Output file. Optional. Prints to console if not provided
  -d, --out-dest            Output target. Optional. Accepted values: console (default), file
  -t, --task                Task to perform. Required. Accepted values: remove-vowels, count-consonants, count-slow-bike
  -h, --help                Show this help message
```

The core processor makes use of SLF4J so that any applications that use it can link it with an
OpenTelemetry service. If you would like to integrate this with an OpenTelemetry service, you must
first download the Java agent JAR from their [GitHub repository](https://github.com/open-telemetry/opentelemetry-java-instrumentation?tab=readme-ov-file).

If you place this JAR in your project you can then integrate with OpenTelemetry:

```
java -javaagent:<path to OpenTelemetry jar> -jar app-cli/target/app-cli-1.0.0.jar [options]
```

### Spring Boot Demonstration Web Application

To run the Spring Boot application, you just need to run:

```
java -jar app-rest/target/app-rest-1.0.0.jar
```

A simple demonstration UI is provided and it can be accessed by default at http://localhost:8080

A Dockerfile is provided for the Spring Boot demo and can be used by executing the following from
the `app-rest` module root:

```shell
docker build -f deploy/Dockerfile -t ais-sport-processor .

docker run -p 8080:8080 ais-sport-processor
```

---

## Deployment Options

The application can be deployed in multiple ways:

- CLI – lightweight, single-user batch processing
- Spring Boot JAR – REST API and web interface
- Docker container – portable and reproducible deployment
- Cloud deployment – containerised app can be deployed to cloud providers (AWS, GCP, Azure etc.)
- Scheduled batch job – CLI app can be run via cron or scheduler for automated file processing

## Future Improvements

- Support for large files / streaming
- Internationalization / Unicode support
- Full API documentation (e.g., Swagger)
- Automated test pipelines, although extensive unit tests are included for all modules
- Stricter input validation for security, especially around file uploads
- A native binary build using GraalVM to remove the Java runtime dependency
