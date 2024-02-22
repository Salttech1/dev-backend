package kraheja.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Broker;
import kraheja.sales.entity.BrokerCK;
@Repository
public interface BrokerRepository extends JpaRepository<Broker, BrokerCK> {

	//@Query("select e  from Broker e WHERE trim(e.brokerCK.brokCode) = :code")
//	Broker findByCode(String code);
	Broker findByBrokerCK_BrokCode(String code);

}