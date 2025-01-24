package com.example.h2.repository;

import com.example.h2.entity.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<TestEntity, Integer> {
}
