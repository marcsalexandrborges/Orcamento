package net.atos.api.orcamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class OrcamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrcamentoApplication.class, args);
	}

}
