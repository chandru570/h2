package com.example.h2.service;

import com.example.h2.entity.TestEntity;
import com.example.h2.repository.TestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
        testRepository.findAll().forEach(x -> list.add(x));
        return new ResponseEntity<String>(list.toString(), HttpStatus.OK);
    }

    public ResponseEntity writeToH2() {
        testRepository.save(new TestEntity("test1", 20, "test1@test.com"));
        return ResponseEntity.ok("row add success");
    }
}
