package com.example.demo.repository;

import com.example.demo.domain.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<Group,Long> {
    List<Group> findAll();
}
