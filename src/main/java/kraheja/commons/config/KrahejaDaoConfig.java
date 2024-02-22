package kraheja.commons.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.commons.repository"  })
@EntityScan(basePackages = { "kraheja.commons.entity" })
public class KrahejaDaoConfig {
	

}