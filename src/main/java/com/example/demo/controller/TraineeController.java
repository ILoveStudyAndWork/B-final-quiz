package com.example.demo.controller;

import com.example.demo.domain.Trainee;
import com.example.demo.service.TraineeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/trainees")
public class TraineeController {

    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    public List<Trainee> getAllTraineeWithoutGrouped(@RequestParam String grouped){
        if ("false".equals(grouped)){
            return traineeService.getAllTraineeWithoutGrouped();
        }
        return Collections.emptyList();
    }
}
