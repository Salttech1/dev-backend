package kraheja.adminexp.overheads.dataentry.repository;

import kraheja.adminexp.overheads.dataentry.entity.Location;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LocationRepository extends JpaRepository<Location,String> 
{
	Location findByCode(String code);
	
}
