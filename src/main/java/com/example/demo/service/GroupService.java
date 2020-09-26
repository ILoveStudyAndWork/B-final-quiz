package com.example.demo.service;

import com.example.demo.domain.Group;
import com.example.demo.domain.Trainee;
import com.example.demo.domain.Trainer;
import com.example.demo.exception.TrainerNotEnoughException;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final static int TRAINER_PER_GROUP = 2;



    public GroupService(GroupRepository groupRepository, TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        this.groupRepository = groupRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
    }

    //TODO GTB：长方法，需要分模块重构
    public List<Group> groupRandomly() throws TrainerNotEnoughException {
        groupRepository.deleteAll();
        List<Trainer> trainerList = trainerRepository.findAll();
        Collections.shuffle(trainerList);
        List<Trainee> traineeList = traineeRepository.findAll();
        Collections.shuffle(traineeList);
        long traineeAmount = traineeRepository.count();
        long trainerAmount =  trainerRepository.count();
        long groupAmount = getGroupAmount(trainerAmount);
        if (groupAmount == 0){
            throw new TrainerNotEnoughException();
        } else {
            Long groupSequence ;
            for (int i = 1; i <= trainerAmount; i++){
                groupSequence = i%groupAmount;
                Group group;
                if (groupRepository.findById(groupSequence).isPresent()){
                    group = groupRepository.findById(groupSequence).get();
                } else {
                    group = Group.builder().name(groupSequence + " 组").build();
                }
                Trainer trainer = trainerList.get(i-1);
                trainer.setGrouped(true);
                group.getTrainers().add(trainer);
                groupRepository.save(group);
            }

            for (int j = 1; j <= traineeAmount; j++){
                groupSequence = j%groupAmount;
                Group group = groupRepository.findById(groupSequence).get();
                Trainee trainee = traineeList.get(j -1);
                trainee.setGrouped(true);
                group.getTrainees().add(trainee);
                groupRepository.save(group);
            }
        }

        return groupRepository.findAll();

    }

    private long getGroupAmount(long trainerAmount){
        return trainerAmount%TRAINER_PER_GROUP == 0 ?
                trainerAmount/TRAINER_PER_GROUP : trainerAmount/TRAINER_PER_GROUP+1;
    }
}
