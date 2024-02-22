package kraheja.commons.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer>{
	
	Set<Menu> findByTreelinkInAndFlgactiveOrderByMenuorderAsc(List<Integer> treelink, String flgactive);

	Set<Menu> findByTreelinkInAndCodeInAndFlgactiveOrderByMenuorderAsc(List<Integer> treelink,Set<Integer> menuCode, String flgactive);

	Menu findByCode(Integer code);
	
	List<Menu> findByCodeIn(Set<Integer> code);
}
