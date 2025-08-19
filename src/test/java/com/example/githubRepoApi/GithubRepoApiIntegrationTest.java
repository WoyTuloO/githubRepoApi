package com.example.githubRepoApi;

import com.example.githubRepoApi.entity.Branch;
import com.example.githubRepoApi.entity.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubRepoApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnUserNonForkedRepositoriesWithBranches() {
        String username = "WoyTuloO";

        ResponseEntity<Repository[]> response = restTemplate.getForEntity(
                "/api/v1/repo/{username}",
                Repository[].class,
                username
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();

        for (Repository repo : response.getBody()) {

            assertThat(repo.getRepositoryName()).isNotBlank();
            assertThat(repo.getOwnerLogin()).isEqualTo(username);

            for (Branch branch : repo.getBranches()) {
                assertThat(branch.getName()).isNotBlank();
                assertThat(branch.getLastCommitSha()).isNotBlank();
            }
        }
    }
}

