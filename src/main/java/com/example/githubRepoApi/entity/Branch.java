package com.example.githubRepoApi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Branch {
    String name;
    String lastCommitSha;
}
