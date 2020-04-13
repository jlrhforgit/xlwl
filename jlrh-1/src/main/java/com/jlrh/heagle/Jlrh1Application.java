package com.jlrh.heagle;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Jlrh1Application {

	public static void main(String[] args) {
		SpringApplication.run(Jlrh1Application.class, args);
		/**
		 * 以下代码是为了验证C3P0数据源是否生效
		 */
//		ConfigurableApplicationContext context = SpringApplication.run(Jlrh1Application.class, args);
//		DataSource dataSource = context.getBean(DataSource.class);
//		System.out.println(dataSource.getClass());
	}

}
