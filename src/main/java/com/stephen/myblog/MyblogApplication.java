package com.stephen.myblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.stephen.myblog.mapper")
public class MyblogApplication {


	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}

}
