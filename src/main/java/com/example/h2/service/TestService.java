package com.example.h2.service;

import com.example.h2.entity.TestEntity;
import com.example.h2.repository.TestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public ResponseEntity<String> readFromH2() {
        List<TestEntity> list = new ArrayList<>();
        testRepository.findAll().forEach(list::add);
        return new ResponseEntity<String>(list.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> writeToH2() {
        try {
            testRepository.save(new TestEntity("test1", 20, "test1@test.com"));
            return ResponseEntity.ok("row add success");
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to add row", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
