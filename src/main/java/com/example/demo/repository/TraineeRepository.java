package com.example.demo.repository;

import com.example.demo.domain.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    List<Trainee> findAllByGroupedIsFalse();
}
