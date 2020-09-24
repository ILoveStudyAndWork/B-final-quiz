package com.example.demo.service;

import com.example.demo.domain.Trainee;
import com.example.demo.repository.TraineeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;
    private final static int INIT_TRAINEE_LENGTH = 35;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
        prepareTrainees();
    }

    public List<Trainee> getAllTraineeWithoutGrouped() {
        return traineeRepository.findAllByGroupedIsFalse();
    }

    public void prepareTrainees(){
        for (int i = 1; i < INIT_TRAINEE_LENGTH; i++) {
            Trainee trainee = Trainee.builder()
                    .email("email@abc.com")
                    .name("学员"+i)
                    .grouped(false)
                    .office("office1")
                    .zoomId("zoom 999")
                    .build();
            traineeRepository.save(trainee);
        }
    }

    public Trainee addTrainee(Trainee trainee) {
        return traineeRepository.save(trainee);
    }

    public void deleteTraineeById(Long traineeId) {
        traineeRepository.deleteById(traineeId);
    }
}
