package com.example.demo.controller;

import com.example.demo.domain.Trainer;
import com.example.demo.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainer addTrainer(@RequestBody @Valid Trainer trainer){
        return trainerService.addTrainer(trainer);
    }

    @GetMapping
    public List<Trainer> getAllTrainerWithoutGrouped(@RequestParam String grouped){
        if ("false".equals(grouped)){
            return trainerService.getAllTrainerWithoutGrouped();
        }
        return Collections.emptyList();
    }
}
