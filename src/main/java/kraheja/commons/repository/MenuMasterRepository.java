//package kraheja.commons.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import kraheja.commons.bean.response.MenuMasterResponseBean;
//import kraheja.commons.entity.MenuMaster;
//
//@Repository
//public interface MenuMasterRepository extends JpaRepository<MenuMaster, Long> {
//    Optional<MenuMaster> findByName(String name);
//
//    @Query(value = "SELECT new kraheja.fd.deposit.bean.response.MenuMasterResponseBean(menu.name AS lev1, t2.name AS lev2, t3.name AS lev3, t4.name AS lev4) "
//    		+ "FROM MenuMaster menu "
//    		+ "LEFT JOIN MenuMaster t2 ON t2.parentId = menu.id "
//    		+ "LEFT JOIN MenuMaster t3 ON t3.parentId = t2.id "
//    		+ "LEFT JOIN MenuMaster t4 ON t4.parentId = t3.id "
//    		+ "WHERE menu.parentId is null")
//	List<MenuMasterResponseBean> findMenu();
//}
