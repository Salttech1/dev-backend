package kraheja.adminexp.overheads.dataentry.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.adminexp.overheads.dataentry.repository"  })
@EntityScan(basePackages = { "kraheja.adminexp.overheads.dataentry" })
public class KrahejaAdminExpDaoConfig {

}
