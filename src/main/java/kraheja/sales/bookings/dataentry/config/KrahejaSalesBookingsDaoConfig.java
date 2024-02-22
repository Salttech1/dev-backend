package kraheja.sales.bookings.dataentry.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "kraheja.sales.bookings.dataentry.repository"  })
@EntityScan(basePackages = { "kraheja.sales.bookings.dataentry.entity" })
public class KrahejaSalesBookingsDaoConfig {
}