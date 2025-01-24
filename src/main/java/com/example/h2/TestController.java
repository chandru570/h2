package com.example.h2;

import com.example.h2.entity.TestEntity;
import com.example.h2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/read")
    public ResponseEntity<String> read() {
        return testService.readFromH2();
    }

    @GetMapping("/write")
    public ResponseEntity<String> write() {
        return testService.writeToH2();
    }
}
