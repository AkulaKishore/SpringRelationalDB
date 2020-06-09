package com.example.SpringRelationalDB;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/Customers")
    public List<Customer> getCustomers(){
         List<Customer> result = jdbcTemplate.query("SELECT * FROM Customer",(rs,rowNum)-> new Customer(rs.getLong(1),rs.getString(2),rs.getString(3)));
         return result;

    }

    @GetMapping("/Customers/{firstName}")
    public List<Customer> getOneCustomer(@PathVariable(value = "firstName") Object[] firstName){
        List<Customer> result  = jdbcTemplate.query("SELECT * FROM Customer where firstName = ? ",firstName,
                (rs,rowNum)-> new Customer(rs.getLong(1),rs.getString(2),rs.getString(3)));
        return result;
    }

}
