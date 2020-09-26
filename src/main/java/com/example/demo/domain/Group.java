package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//TODO GTB：group是数据库保留字段
@Table(name = "group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //TODO GTB：思考一下，group这边需要维护这两个list吗？
    private String name;
    @OneToMany(mappedBy = "group")
    private List<Trainer> trainers;

    @OneToMany( mappedBy = "group")
    private List<Trainee> trainees;
}
