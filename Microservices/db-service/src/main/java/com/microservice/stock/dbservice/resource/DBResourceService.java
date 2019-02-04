package com.microservice.stock.dbservice.resource;

import com.microservice.stock.dbservice.model.Quote;
import com.microservice.stock.dbservice.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DBResourceService {

    @Autowired
    private QuotesRepository quotesRepository;

    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username")
                                              final String username){
        return quotesRepository.findByUserName(username)
        .stream()
        .map(Quote::getQuote).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public List<String> add(@RequestBody final Quote quote){

    }
}
