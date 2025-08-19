package com.example.githubRepoApi.controler;

import com.example.githubRepoApi.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/repo/{username}")
    public ResponseEntity<?> getUserRepositories(@PathVariable String username) {
        return ResponseEntity.ok(apiService.getUsersRepositories(username));
    }

}
