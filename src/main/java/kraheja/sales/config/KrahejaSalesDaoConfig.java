package kraheja.sales.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.sales.repository"  })
@EntityScan(basePackages = { "kraheja.sales.entity" })
public class KrahejaSalesDaoConfig {
}