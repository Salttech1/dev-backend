package kraheja.adminexp.vehicleexp.dataentry.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.adminexp.vehicleexp.dataentry.repository"  })
@EntityScan(basePackages = { "kraheja.adminexp.vehicleexp.dataentry" })
public class KrahejaAdminExpVehicleDaoConfig {

}
