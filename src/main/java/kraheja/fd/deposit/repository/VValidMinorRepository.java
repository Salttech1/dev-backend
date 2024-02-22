package kraheja.fd.deposit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.fd.deposit.entity.VValidminor;

@Repository
public interface VValidMinorRepository extends JpaRepository<VValidminor, String>{

}