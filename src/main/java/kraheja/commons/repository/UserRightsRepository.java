package kraheja.commons.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Userright;
import kraheja.commons.entity.UserrightCK;

@Repository
public interface UserRightsRepository extends JpaRepository<Userright, UserrightCK>{

		@Query("select e.userrightCK.usrtModulerightcode from Userright e WHERE trim(e.userrightCK.usrtStaffcode) = trim(:staffCode)")
		List<Integer> findByUsrtStaffcode(String staffCode); 
		
//		List<Integer> findByUsrtStaffcodeAndUserrightCK_UsrtModulerightcode(String staffCode, Integer modulercode); 
		
		@Query("select r from Userright r WHERE  trim(r.userrightCK.usrtStaffcode)= :staffCode AND r.usrtDropiconsyn='Y' AND r.userrightCK.usrtModulerightcode	IN (select e.mdrgCode from Moduleright e where e.mdrgCode in (select userright.userrightCK.usrtModulerightcode from Userright userright WHERE trim(userright.userrightCK.usrtStaffcode) = trim(:staffCode)))")
		Set<Userright> findByMdrgCodeIn(String staffCode);
		
		@Modifying
		@Query("UPDATE Userright r SET r.usrtDropiconsyn = :dropIcon WHERE trim(r.userrightCK.usrtStaffcode) = :username AND  r.userrightCK.usrtModulerightcode in (select e.mdrgCode from Moduleright e where e.modulerightCK.mdrgMenucd in (SELECT m.code FROM Menu m WHERE m.code in :menuCode) AND e.modulerightCK.mdrgRightsoptioncd = 1)")
		public void updatefavourite(String dropIcon, String username, Set<Integer> menuCode);
}
