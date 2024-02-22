package kraheja.purch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.bean.response.AuthDCancelMaterialBean;
import kraheja.purch.entity.Auth_D ;
import kraheja.purch.entity.Auth_DCK;
@Repository
public interface AuthDRepository extends JpaRepository<Auth_D, Auth_DCK> {

	 
	@Query("select e from Auth_D e where e.auth_dCK.autdAuthnum = :authnum")
	List<Auth_D> findByAuthdCK_AutdAuthnum(String authnum) ; 
	
	@Query("Select new kraheja.purch.bean.response.AuthDCancelMaterialBean(a.auth_dCK.autdSuppbillno, to_char(a.autdSuppbilldt,'dd/MM/yyyy'), a.autdAuthqty, a.autdAuthamount, a.autdAuthtdsamt, a.autdRetainamt, a.autdRelretamt ,a.autdAdvadj, a.autdRetentionadj) from Auth_D a where trim(a.auth_dCK.autdAuthnum) = :authnum")
	List<AuthDCancelMaterialBean> findAuthDetailsByAuthNum(String authnum);

	
	@Modifying
	@Query("Delete from Auth_D a where trim(a.auth_dCK.autdAuthnum) = :authNum")
	void deleteAuthDByAuthNum(String authNum);

}