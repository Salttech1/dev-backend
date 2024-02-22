package kraheja.commons.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Moduleright;
import kraheja.commons.entity.ModulerightCK;

@Repository
public interface ModulerRightsRepository extends JpaRepository<Moduleright, ModulerightCK> {

	@Query("select e from Moduleright e where e.mdrgCode in (select userright.userrightCK.usrtModulerightcode from Userright userright WHERE trim(userright.userrightCK.usrtStaffcode) = trim(:staffCode))")
	public Set<Moduleright> findByMdrgCodeIn(String staffCode);
	
}
