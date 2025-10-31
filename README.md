Quartz Scheduler Application
============================

A robust Spring Boot REST application that schedules jobs using Quartz Scheduler with persistence in an H2 database.

Prerequisites
-------------

-   Java 17 or higher

    -   Apache Maven

How to Run
----------

-   Clone the repository.

    -   Navigate to the project's root directory.

    -   Clean and build the project using Maven:



    ```
    mvn clean install
    ```

    -   Run the application:



    ```
    java -jar target/quartz-scheduler-app-0.0.1-SNAPSHOT.jar
    ```

The application will be running on http://localhost:8080. A local H2 database file will be created in the /data directory.

* * * * *

API Endpoint
------------

### Schedule a Job

Schedules a new job to print a message at a specified time in the future.

-   **URL**: /api/v1/schedule/job

    -   **Method**: POST

    -   **Content-Type**: application/json

#### Example Request (Successful)

The scheduledTime must be a future date and time.



```
{
    "name": "ABC",
    "username": "architect2",
    "message": "Welcome to NHK",
    "scheduledTime": "2025-12-02T10:30:00"
}
```

#### Success Response (202 Accepted)


```
{
    "success": true,
    "message": "Job scheduled successfully!"
}
```

* * * * *

#### Example Request (Validation Error)

Providing a scheduledTime that is in the past will result in a validation error.



```
{
    "name": "ABC",
    "username": "architect2",
    "message": "Welcome to NHK",
    "scheduledTime": "2024-12-02T10:30:00"
}
```

#### Error Response (400 Bad Request)



```
{
    "success": false,
    "errors": {
        "scheduledTime": "Scheduled time must be in the future."
    }
}
```

* * * * *

Database
--------

The application uses a file-based H2 database to persist all scheduled jobs. The Quartz tables are automatically created on the first startup.

### How to Check Scheduled Jobs

You can query the Quartz tables to see the details of the jobs that have been scheduled.

**Example Query:**

This query retrieves all persisted job details from the primary Quartz table. The actual job data (name, message, etc.) is stored in a serialized format in the JOB_DATA column.



```
select * from QRTZ_JOB_DETAILS;
```
