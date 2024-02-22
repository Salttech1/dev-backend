package kraheja.purch.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.purch.repository"  })
@EntityScan(basePackages = { "kraheja.purch.entity" })
public class KrahejaPurchDaoConfig {
}