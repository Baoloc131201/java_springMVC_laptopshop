package com.example.CURD_java_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CurdJavaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurdJavaProjectApplication.class, args);
		Predicate<Integer>
				isEven = number -> number % 2 == 0;
		Predicate<Integer>
				isPositive = number -> number > 3;

		// Filtering with a single predicate
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<Integer> evenNumbers = numbers.stream()
				.filter(isEven)
				.toList();
		System.out.println("Event-Number" + evenNumbers);

		// Combining predicates
		Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
		List<Integer> evenPositiveNumbers = numbers.stream()
				.filter(isEvenAndPositive)
				.toList();
		System.out.println("Event-Number-Positive" + evenPositiveNumbers);
	}

}
