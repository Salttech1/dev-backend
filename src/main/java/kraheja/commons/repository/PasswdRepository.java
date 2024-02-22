package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Passwd;

@Repository
public interface PasswdRepository extends JpaRepository<Passwd, String>{

	@Query("select e  from Passwd e WHERE trim(e.userlog) = :userlog")
	Passwd findByUserlogIgnoreCase(String userlog); 
	
	@Query("select e  from Passwd e WHERE trim(e.angularUser) = :username")
	Passwd findByAngularUserIgnoreCase(String username);
}
