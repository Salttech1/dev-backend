package kraheja.fd.deposit.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.fd.deposit.repository"  })
@EntityScan(basePackages = { "kraheja.fd.deposit.entity" })
public class KrahejaFDDaoConfig {
}