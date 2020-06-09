package com.example.SpringRelationalDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringRelationalDbApplication implements CommandLineRunner {
	public static final Logger log = LoggerFactory.getLogger(SpringRelationalDbApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringRelationalDbApplication.class, args);
	}
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String...strings) throws Exception{
		log.info("Creating a Customer table");
		jdbcTemplate.execute("DROP TABLE Customer IF EXISTS ");
		jdbcTemplate.execute("CREATE TABLE Customer( Id SERIAL , FirstName VARCHAR(255), LastName VARCHAR(255))");

		//convert full names into array of firstName and lastName
		List<Object[]> splitupNames = Arrays.asList("Kishore Akula","Sunanda Chuthari","Spoorthi Akula","VenkatRamulu Akula")
				.stream().map(name->name.split(" ")).collect(Collectors.toList());
        //using java 8 stream to print out each tuple in the list;
		splitupNames.forEach(name-> log.info(String.format("Inserting customer record for %s,%s",name[0],name[1])));
		jdbcTemplate.batchUpdate("INSERT INTO Customer(FirstName,LastName) VALUES(?,?)",splitupNames);
		//search for the details of firstName = sunanda;
//		jdbcTemplate.query("SELECT * FROM Customer WHERE firstName = ?",new Object[]{"Sunanda"},(rs,rowNum)->new Customer(rs.getLong(1),rs.getString(2),rs.getString(3)))
//				.forEach(customer -> log.info(customer.toString()));
	}

}
