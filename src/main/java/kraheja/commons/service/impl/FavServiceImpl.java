package kraheja.commons.service.impl;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ProprietorResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Menu;
import kraheja.commons.entity.Moduleright;
import kraheja.commons.entity.Passwd;
import kraheja.commons.entity.Proprietor;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.MenuRepository;
import kraheja.commons.repository.ModulerRightsRepository;
import kraheja.commons.repository.PasswdRepository;
import kraheja.commons.repository.RightsOptionRepository;
import kraheja.commons.repository.UserRightsRepository;
import kraheja.commons.service.FavService;
import kraheja.commons.utils.CommonUtils;

@Service
@Transactional
public class FavServiceImpl implements FavService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private PasswdRepository  passwdRepository;

	@Autowired
	private MenuRepository  menuRepository; 

	@Autowired
	private RightsOptionRepository  rightsOptionRepository;
	
	@Autowired
	private UserRightsRepository  userRightsRepository;

	@Autowired
	private ModulerRightsRepository  modulerRightsRepository;

	@Override
	public ResponseEntity<?> addFavs(Set<Integer> menucodes) {
		Passwd passwdEntity = this.passwdRepository.findByAngularUserIgnoreCase(GenericAuditContextHolder.getContext().getUserid());
		
		List<Menu> menuEntityList = this.menuRepository.findByCodeIn(menucodes);
		
		//TODO :: This needs to change or else userights table will always have X user names instead of Y (needs discussion)
		Set<Moduleright> faModulerRightsEntityList = this.modulerRightsRepository.findByMdrgCodeIn(passwdEntity.getUserlog());
		
		Map<Integer, List<Moduleright>>  MenuCodeToModuleRightMap = faModulerRightsEntityList.stream().filter(filter -> filter.getModulerightCK().getMdrgRightsoptioncd().equals(BigInteger.ONE.intValue())).collect(Collectors.groupingBy(faModulerRightsEntity ->faModulerRightsEntity.getModulerightCK().getMdrgMenucd()));
		
//		this.userRightsRepository.findByUsrtStaffcodeAndUserrightCK_UsrtModulerightcodeIn(passwdEntity.getUserlog(), );
		
		return null;
	}
}
























