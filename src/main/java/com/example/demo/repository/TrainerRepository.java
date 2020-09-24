package com.example.demo.repository;

import com.example.demo.domain.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {
    List<Trainer> findAllByGroupedIsFalse();
    List<Trainer> findAll();
}
