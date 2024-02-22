package kraheja.adminexp.billing.dataentry.debitNote.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.adminexp.billing.dataentry.debitNote.repository"  })
@EntityScan(basePackages = { "kraheja.adminexp.billing.dataentry.debitNote.entity" })
public class DebitNoteDaoConfig {

}
