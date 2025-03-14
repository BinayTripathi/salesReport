package com.tabcorp.saleReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.tabcorp.saleReport")
@EntityScan("com.tabcorp.saleReport")
public class SaleReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleReportApplication.class, args);
	}

}
