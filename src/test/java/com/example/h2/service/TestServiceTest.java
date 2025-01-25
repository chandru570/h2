package com.example.h2.service;

import com.example.h2.entity.TestEntity;
import com.example.h2.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceTest {

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestService testService;

    @BeforeEach
    void setUp() {
        testService = new TestService(testRepository);
    }

    @Test
    void testReadFromH2() {
        List<TestEntity> testEntities = Arrays.asList(
                new TestEntity("test1", 20, "test1@test.com"),
                new TestEntity("test2", 25, "test2@test.com")
        );
        when(testRepository.findAll()).thenReturn(testEntities);

        ResponseEntity<String> response = testService.readFromH2();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testEntities.toString(), response.getBody());
        verify(testRepository, times(1)).findAll();
    }

    @Test
    void testWriteToH2() {
        TestEntity testEntity = new TestEntity("test1", 20, "test1@test.com");
        when(testRepository.save(any(TestEntity.class))).thenReturn(testEntity);

        ResponseEntity<String> response = testService.writeToH2();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("row add success", response.getBody());
        verify(testRepository, times(1)).save(any(TestEntity.class));
    }

    @Test
    void readFromH2ReturnsEmptyListWhenNoData() {
        when(testRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<String> response = testService.readFromH2();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[]", response.getBody());
        verify(testRepository, times(1)).findAll();
    }

    @Test
    void writeToH2ReturnsErrorWhenSaveFails() {
        when(testRepository.save(any(TestEntity.class))).thenThrow(new RuntimeException("Save failed"));

        ResponseEntity<String> response = testService.writeToH2();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to add row", response.getBody());
        verify(testRepository, times(1)).save(any(TestEntity.class));
    }
}