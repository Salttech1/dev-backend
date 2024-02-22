package kraheja.adminexp.billing.dataentry.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.adminexp.billing.dataentry.repository"  })
@EntityScan(basePackages = { "kraheja.adminexp.billing.dataentry.entity" })
public class BillingDaoConfig {

}
