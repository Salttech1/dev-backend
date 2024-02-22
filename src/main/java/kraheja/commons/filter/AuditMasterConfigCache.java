package kraheja.commons.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kraheja.commons.entity.Menu;
import kraheja.commons.repository.MenuRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuditMasterConfigCache {
	
	@Autowired
	MenuRepository menuRepository;
	
	private static Map<Integer, Menu> auditMasterConfigMap = new HashMap<>();
	
	@PostConstruct
	void initCache(){
		List<Menu> menuList = menuRepository.findAll();
		auditMasterConfigMap = menuList.stream().collect(Collectors.toMap(Menu::getCode, Function.identity()));
	}
	
	public Menu fetchCachedTables(Integer code) {
		return auditMasterConfigMap.get(code);
	}
}

