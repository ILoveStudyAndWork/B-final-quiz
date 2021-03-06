package com.example.demo.controller;

import com.example.demo.domain.Trainee;
import com.example.demo.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainee addTrainer(@RequestBody @Valid Trainee trainee){
        return traineeService.addTrainee(trainee);
    }

    @GetMapping
    public List<Trainee> getAllTraineeWithoutGrouped(@RequestParam String grouped){
        //TODO GTB：需要抽取变量
        if ("false".equals(grouped)){
            return traineeService.getAllTraineeWithoutGrouped();
        }
        return Collections.emptyList();
    }

    @DeleteMapping("/{trainee_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTraineeById(@PathVariable Long trainee_id){
        traineeService.deleteTraineeById(trainee_id);
    }

}
