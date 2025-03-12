package com.expenseBuddy;

import com.expenseBuddy.model.TestEntity;
import com.expenseBuddy.repository.TestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpenseBuddyApplication {

	public static void main(String[] args) {

		  SpringApplication.run(ExpenseBuddyApplication.class, args);
	}

	@Bean
	CommandLineRunner run(TestRepository testRepository){
		return args -> {
			TestEntity entity = new TestEntity();

			entity.setName("Test Record");
			testRepository.save(entity);
			System.out.println("Record saved!");
		};
	}
}
