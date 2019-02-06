package com.database.service.databaseservice.resource;

import com.database.service.databaseservice.model.Employee;
import com.database.service.databaseservice.repository.DbRepository;
import org.hibernate.SessionFactory;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@RestController
@RequestMapping("/v1/api/employees")
public class DbServiceController {

    private DbRepository dbRepository;

    public DbServiceController(DbRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @GetMapping("/")
    public List<Employee> getDetails(){

        return dbRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee getByEmployeeId(@PathVariable("id") int id){
        return dbRepository.findAllById(id);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public Employee addEmployee(@RequestBody Employee emp){
        return dbRepository.save(emp);
    }


    /*
    if we have 100 fields we cant check null,
     for each item that becomes redundant in update
     need to rewrite it later
     */
    @PutMapping(value = "/{id}", consumes = "application/json")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee emp){
       Employee empl = dbRepository.findAllById(id);

       if(emp.getAddress() != null)
       empl.setAddress(emp.getAddress());

       if(emp.getName() != null)
       empl.setName(emp.getName());
       return dbRepository.save(empl);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") int id){
        dbRepository.deleteById(id);
    }
}
