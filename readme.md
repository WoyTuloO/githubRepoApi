# GitHub Repo API

##  Description

A Spring Boot REST API application that returns a list of a **GitHub user's public repositories**, excluding forks.
For each repository, the following information is returned:

* **repository name**,
* **owner login**,
* **list of branches** with branch name and last `commit sha`.

If a **non-existing user** is requested, the API returns a `404` response in JSON format.

---

##  Technologies

* Java 21
* Spring Boot 3.5
* [GitHub API (org.kohsuke.github)](https://github.com/hub4j/github-api)
* JUnit 5
* TestRestTemplate (Spring Boot Test)
* Swagger / OpenAPI (springdoc-openapi)

---

##  Project Structure

```
src/main/java/com/example/githubRepoApi
 ├── controler/ApiController.java        # REST API
 ├── service/ApiService.java             # Business logic
 ├── entity/Repository.java, Branch.java # Data models
 ├── exceptionHandler/                   # Error handling
 │    ├── GlobalExceptionHandler.java
 │    ├── UserNotFoundException.java
 │    └── GitHubApiException.java
 └── GithubRepoApiApplication.java       # Main application class
```

---

##  Endpoints

###  List user repositories

**Request**

```
GET /api/v1/repo/{username}
```

**Response 200**

```json
[
  {
    "repositoryName": "example-repo",
    "ownerLogin": "someUser",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "abc123def456"
      }
    ]
  }
]
```

**Response 404 (user not found)**

```json
{
  "status": 404,
  "message": "User someUser not found"
}
```

---

##  Tests

* `GithubRepoApiIntegrationTest` – integration test verifying the **happy path**:

  * fetching user repositories,
  * verifying repositories are not forks,
  * checking branches and `commit sha`.

---

##  Running the Application

### 1. Build the project

```bash
./mvnw clean install
```

### 2. Start the application

```bash
./mvnw spring-boot:run
```

The application will be available at:
[http://localhost:8080/api/v1/repo/{username}](http://localhost:8080/api/v1/repo/{username})

### 3. Swagger Documentation

Once the application is running, OpenAPI/Swagger documentation is available at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

You can easily test endpoints directly from the browser as well.

---

##  Notes

* API works anonymously (without GitHub token), which limits requests to **60/hour**.
