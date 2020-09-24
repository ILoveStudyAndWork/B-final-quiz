package com.example.demo.controller;

import com.example.demo.domain.Group;
import com.example.demo.exception.TrainerNotEnoughException;
import com.example.demo.service.GroupService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/auto-grouping")
    public List<Group> groupRandomly() throws TrainerNotEnoughException {
        return groupService.groupRandomly();

    }
}
