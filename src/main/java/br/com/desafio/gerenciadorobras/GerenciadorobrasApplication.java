package br.com.desafio.gerenciadorobras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GerenciadorObrasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorObrasApplication.class, args);
	}

}
