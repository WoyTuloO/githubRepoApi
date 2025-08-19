package com.example.githubRepoApi.exceptionHandler;

public class GitHubApiException extends RuntimeException {
    public GitHubApiException(String message) {
        super(message);
    }
}
