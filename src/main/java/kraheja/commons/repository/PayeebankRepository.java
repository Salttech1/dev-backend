package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Payeebank;

@Repository
public interface PayeebankRepository extends JpaRepository<Payeebank, String> , CrudRepository<Payeebank, String>{

	Payeebank findByPybkCode(String payeeBankCode);
}
