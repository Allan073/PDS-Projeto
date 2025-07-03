package br.ufrn.imd.cbm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"br.ufrn.imd.framework","br.ufrn.imd.cbm"})
@EntityScan(basePackages = {"br.ufrn.imd.framework","br.ufrn.imd.cbm"})
@EnableJpaRepositories(basePackages = {"br.ufrn.imd.framework","br.ufrn.imd.cbm"})
public class CbmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CbmApplication.class, args);
	}

}
