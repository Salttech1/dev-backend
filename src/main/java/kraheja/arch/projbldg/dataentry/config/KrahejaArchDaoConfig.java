package kraheja.arch.projbldg.dataentry.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.arch.projbldg.dataentry.repository"  })
@EntityScan(basePackages = { "kraheja.arch.projbldg.dataentry.entity" })
public class KrahejaArchDaoConfig {
}
