package com.example.githubRepoApi.service;

import com.example.githubRepoApi.entity.Branch;
import com.example.githubRepoApi.entity.Repository;
import com.example.githubRepoApi.exceptionHandler.GitHubApiException;
import com.example.githubRepoApi.exceptionHandler.UserNotFoundException;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ApiService {
    private final GitHub github;

    public ApiService() {
        try {
            this.github = GitHub.connectAnonymously();
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to GitHub", e);
        }
    }

    public PagedIterable<GHRepository> getRepos(String username){
        try {
            return github.getUser(username).listRepositories();
        } catch (IOException e) {
            if(e.getMessage().contains("Not Found")) {
                throw new UserNotFoundException("User " + username + " not found");
            }
            throw new GitHubApiException("GitHub API is currently unavailable.");
        }
    }

    public List<Repository> getUsersRepositories(String username){
        PagedIterable<GHRepository> ghRepositories = getRepos(username);

        try {
            return ghRepositories.toList().stream()
                    .filter(repo -> !repo.isFork())
                    .map(this::mapToRepository)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Error when parsing repositories",e);
        }
    }

    public Repository mapToRepository(GHRepository repo) {
        Repository repository = new Repository(repo.getName(), repo.getOwnerName());

        try {
            repository.setBranches(repo.getBranches().values().stream()
                    .map(branch -> new Branch(branch.getName(), branch.getSHA1())
                    ).toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to get branches for repository: " + repo.getName(), e);
        }
        return repository;

    }

}
