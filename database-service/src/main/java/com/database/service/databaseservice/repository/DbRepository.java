package com.database.service.databaseservice.repository;

import com.database.service.databaseservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DbRepository extends CrudRepository<Employee, Integer> {

    List<Employee> findAll();

    Employee findAllById(int id);

    Employee save(Employee emp);

}
