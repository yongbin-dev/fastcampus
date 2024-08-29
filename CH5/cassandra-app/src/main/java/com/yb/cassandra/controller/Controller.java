package com.yb.cassandra.controller;


import com.yb.cassandra.service.CassandraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    CassandraService cassandraService;

    @GetMapping("/cas-test")
    public void cassandraTest() {
        cassandraService.casTest();
    }
}
