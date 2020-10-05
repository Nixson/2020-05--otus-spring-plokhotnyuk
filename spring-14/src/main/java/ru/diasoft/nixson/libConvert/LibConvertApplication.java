package ru.diasoft.nixson.libConvert;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class LibConvertApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibConvertApplication.class, args);
	}

}
