package kraheja.payroll.config;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.payroll.repository"  })
@EntityScan(basePackages = { "kraheja.payroll.entity" })

public class KrahejaPayrollDaoConfig {

}