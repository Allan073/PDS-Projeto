package br.ufrn.imd.cbm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.ufrn.imd.framework")
public class CbmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CbmApplication.class, args);
	}

}
