package com.example.githubRepoApi.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Repository {
    private final String repositoryName;
    private final String ownerLogin;
    List<Branch> branches;
}
