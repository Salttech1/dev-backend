package kraheja.commons.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.request.FavouriteRequestBeanList;
import kraheja.commons.bean.response.FaMenuResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Menu;
import kraheja.commons.entity.Moduleright;
import kraheja.commons.entity.Passwd;
import kraheja.commons.entity.Rightsoption;
import kraheja.commons.entity.Userright;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.MenuRepository;
import kraheja.commons.repository.ModulerRightsRepository;
import kraheja.commons.repository.PasswdRepository;
import kraheja.commons.repository.RightsOptionRepository;
import kraheja.commons.repository.UserRightsRepository;
import kraheja.commons.service.MenuService;

@Transactional
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private PasswdRepository  passwdRepository;

	@Autowired
	private MenuRepository  menuRepository; 

	@Autowired
	private RightsOptionRepository  rightsOptionRepository;

	@Autowired
	private ModulerRightsRepository  modulerRightsRepository;

	@Autowired
	private UserRightsRepository  userRightsRepository;

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public ResponseEntity<?> fetchMenu(String username) {
		Passwd passwdEntity = this.passwdRepository.findByAngularUserIgnoreCase(username.trim());

		Set<Menu> menuEntityList = null;

		Set<Moduleright> faModulerRightsEntityList = new HashSet<>();

		if(Objects.nonNull(passwdEntity.getUserlog())&& passwdEntity.getUsersup().equals("N")) { // Removed this super user check 18.05.2032  -> 
			faModulerRightsEntityList = this.modulerRightsRepository.findByMdrgCodeIn(passwdEntity.getUserlog());
			//			logger.info("FaModulerRightsEntity :: {}" + faModulerRightsEntityList);
		}
		if(Objects.nonNull(passwdEntity.getUserlog())&& passwdEntity.getUsersup().equals("Y")) {
			faModulerRightsEntityList = this.modulerRightsRepository.findByMdrgCodeIn(passwdEntity.getUserlog());
			menuEntityList = this.menuRepository.findByTreelinkInAndFlgactiveOrderByMenuorderAsc(Arrays.asList(0, 1, 2, 3), "Y");
		}			

		if(faModulerRightsEntityList != null && !faModulerRightsEntityList.isEmpty() && passwdEntity.getUsersup().equals("N") ) {
			menuEntityList = this.menuRepository.findByTreelinkInAndCodeInAndFlgactiveOrderByMenuorderAsc(Arrays.asList(0, 1, 2, 3), faModulerRightsEntityList.stream().map(modulerrights -> modulerrights.getModulerightCK().getMdrgMenucd()).collect(Collectors.toSet()), "Y");
			//			logger.info("FaMenuEntityList Zeroth Lev Inside If :: {}" + menuEntityList);
		}else //If in case any user not able to view all menu assigned to him/her, then remove else block and shift the below line to line no. 76
			menuEntityList = this.menuRepository.findByTreelinkInAndFlgactiveOrderByMenuorderAsc(Arrays.asList(0, 1, 2, 3), "Y");
		//		logger.info("FaMenuEntityList Zeroth Lev:: {}" + menuEntityList);

		Map<Object, List<Menu>> groupByParentMenuMap = menuEntityList.parallelStream().collect(Collectors.groupingBy(menuEntity -> menuEntity.getNeumonic().charAt(0), LinkedHashMap::new, Collectors.toList()));
		//		logger.info("GroupByParentMenu :: {} " + groupByParentMenuMap);

		Map<Object, List<Menu>> groupByParentSubLevelTwoMenuMap = menuEntityList.parallelStream().filter(menuEntity -> menuEntity.getNeumonic().length() > 2).collect(Collectors.groupingBy(faMenuEntity -> faMenuEntity.getNeumonic().substring(0, 2), LinkedHashMap::new, Collectors.toList()));
		//		logger.info("GroupByParentSubLevelTwoMenuMap :: {} " + groupByParentSubLevelTwoMenuMap);

		Map<Object, List<Menu>> groupByParentSubLevelThreeMenuMap = menuEntityList.parallelStream().filter(menuEntity -> menuEntity.getNeumonic().length() > 3).collect(Collectors.groupingBy(faMenuEntity -> faMenuEntity.getNeumonic().substring(0, 3), LinkedHashMap::new, Collectors.toList()));
		//		logger.info("GroupByParentSubLevelThreeMenuMap :: {} " + groupByParentSubLevelThreeMenuMap);

		Map<Long,String> accessRightsMap = this.rightsOptionRepository.findAll().stream().collect(Collectors.toMap(Rightsoption::getRgopCode, Rightsoption::getRgopDescription));
		//		logger.info("AccessRightsMap :: {}" , accessRightsMap);

		Map<Object, List<Moduleright>>  MenuCodeToModuleRightMap = faModulerRightsEntityList.stream().collect(Collectors.groupingBy(faModulerRightsEntity ->faModulerRightsEntity.getModulerightCK().getMdrgMenucd()));
		//		logger.info("MenuCodeToModuleRightMap :: {}" , MenuCodeToModuleRightMap);

		Set<Userright> userRightEntityList = this.userRightsRepository.findByMdrgCodeIn(passwdEntity.getUserlog().trim());
		//		logger.info("userRightEntityList :: {}" , userRightEntityList);

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(menuEntityList.stream().filter(menuEntity -> menuEntity.getTreelink() == 0).map(menuEntity -> {
			//==========================================================================================================================(Zero Level)=========================
			FaMenuResponseBean.FaMenuResponseBeanBuilder faMenuResponseBean = FaMenuResponseBean.builder();

			faMenuResponseBean
			.faMenucode(menuEntity.getCode())
			.faMenuDescription(menuEntity.getDescription())
			.faMenuFlgactive(menuEntity.getFlgactive())
			.faMenuMenuorder(menuEntity.getMenuorder())
			.faMenuNeumonic(menuEntity.getNeumonic())
			.faMenuSepgroup(menuEntity.getSepgroup())
			.faMenuShortcutkey(menuEntity.getShortcutkey())
			.faMenuTreelevel(menuEntity.getTreelevel())
			.faFormName(menuEntity.getFormname())
			.faMenuUsersup(passwdEntity.getUsersup())
			.faMenuTreelink(menuEntity.getTreelink());
			if((MenuCodeToModuleRightMap != null && !MenuCodeToModuleRightMap.isEmpty()) && MenuCodeToModuleRightMap.containsKey(menuEntity.getCode())) {
				faMenuResponseBean.rightsOptionsList(MenuCodeToModuleRightMap.get(menuEntity.getCode()).stream().map(menuCodeToModuleRight -> accessRightsMap.containsKey(menuCodeToModuleRight.getModulerightCK().getMdrgRightsoptioncd().longValue()) ? accessRightsMap.get(menuCodeToModuleRight.getModulerightCK().getMdrgRightsoptioncd().longValue()) : null).collect(Collectors.toList()));
			}
			if(CollectionUtils.isNotEmpty(userRightEntityList) && MenuCodeToModuleRightMap.containsKey(menuEntity.getCode())) {
				MenuCodeToModuleRightMap.get(menuEntity.getCode()).stream().map(menuCodeToModuleRight ->{
					userRightEntityList.stream().filter(f ->  f.getUserrightCK().getUsrtModulerightcode().equals(menuCodeToModuleRight.getMdrgCode())).map(userRightEntity -> {
						faMenuResponseBean.favourite("Y");
						return userRightEntity;
					}).collect(Collectors.toList());
					return menuCodeToModuleRight;
				}).collect(Collectors.toList());
			}

			//==========================================================================================================================(First Level)=========================
			if((groupByParentMenuMap != null || !groupByParentMenuMap.isEmpty()) && groupByParentMenuMap.containsKey(menuEntity.getNeumonic().charAt(0))) {
				faMenuResponseBean.faMenuResponseBeanList(
						groupByParentMenuMap.get(menuEntity.getNeumonic().charAt(0)).stream().filter(faSubLevFirstMenuEntity -> faSubLevFirstMenuEntity.getTreelink() == 1).map(groupBySubMenuFirstParentMenu ->{
							FaMenuResponseBean.FaMenuResponseBeanBuilder faSubMenuFirstResponseBean = FaMenuResponseBean.builder();

							faSubMenuFirstResponseBean
							.faMenucode(groupBySubMenuFirstParentMenu.getCode())
							.faMenuDescription(groupBySubMenuFirstParentMenu.getDescription())
							.faMenuFlgactive(groupBySubMenuFirstParentMenu.getFlgactive())
							.faMenuMenuorder(groupBySubMenuFirstParentMenu.getMenuorder())
							.faMenuNeumonic(groupBySubMenuFirstParentMenu.getNeumonic())
							.faMenuSepgroup(groupBySubMenuFirstParentMenu.getSepgroup())
							.faMenuShortcutkey(groupBySubMenuFirstParentMenu.getShortcutkey())
							.faFormName(groupBySubMenuFirstParentMenu.getFormname())
							.faMenuTreelevel(groupBySubMenuFirstParentMenu.getTreelevel())
							.faMenuUsersup(passwdEntity.getUsersup())
							.faMenuTreelink(groupBySubMenuFirstParentMenu.getTreelink());
							if((MenuCodeToModuleRightMap != null && !MenuCodeToModuleRightMap.isEmpty()) && MenuCodeToModuleRightMap.containsKey(groupBySubMenuFirstParentMenu.getCode())) {
								faSubMenuFirstResponseBean.rightsOptionsList(MenuCodeToModuleRightMap.get(groupBySubMenuFirstParentMenu.getCode()).stream().map(menuCodeToModuleRight -> accessRightsMap.containsKey(menuCodeToModuleRight.getModulerightCK().getMdrgRightsoptioncd().longValue()) ? accessRightsMap.get(menuCodeToModuleRight.getModulerightCK().getMdrgRightsoptioncd().longValue()) : null).collect(Collectors.toList()));
							}
							//TO SET FAVOURITES 
							if(CollectionUtils.isNotEmpty(userRightEntityList) && MenuCodeToModuleRightMap.containsKey(groupBySubMenuFirstParentMenu.getCode())) {
								MenuCodeToModuleRightMap.get(groupBySubMenuFirstParentMenu.getCode()).stream().map(menuCodeToModuleRight ->{
									userRightEntityList.stream().filter(f ->  f.getUserrightCK().getUsrtModulerightcode().equals(menuCodeToModuleRight.getMdrgCode())).map(userRightEntity -> {
										faSubMenuFirstResponseBean.favourite("Y");
										return userRightEntity;
									}).collect(Collectors.toList());
									return menuCodeToModuleRight;
								}).collect(Collectors.toList());
							}
							//==========================================================================================================================(Second Level)=========================
							if((groupByParentSubLevelTwoMenuMap != null || !groupByParentSubLevelTwoMenuMap.isEmpty())  && groupByParentSubLevelTwoMenuMap.containsKey(groupBySubMenuFirstParentMenu.getNeumonic().substring(0, 2))) {
								faSubMenuFirstResponseBean.faMenuResponseBeanList(
										groupByParentSubLevelTwoMenuMap.get(groupBySubMenuFirstParentMenu.getNeumonic().substring(0, 2)).stream().sorted(Comparator.comparing(Menu::getNeumonic)).filter(faSubLevSecondMenuEntity -> faSubLevSecondMenuEntity.getTreelink() == 2).map(groupBySubMenuSecondParentMenu ->{
											FaMenuResponseBean.FaMenuResponseBeanBuilder faSubMenuSecondMenuResponseBean = FaMenuResponseBean.builder();
											faSubMenuSecondMenuResponseBean
											.faMenucode(groupBySubMenuSecondParentMenu.getCode())
											.faMenuDescription(groupBySubMenuSecondParentMenu.getDescription())
											.faMenuFlgactive(groupBySubMenuSecondParentMenu.getFlgactive())
											.faMenuMenuorder(groupBySubMenuSecondParentMenu.getMenuorder())
											.faMenuNeumonic(groupBySubMenuSecondParentMenu.getNeumonic())
											.faMenuSepgroup(groupBySubMenuSecondParentMenu.getSepgroup())
											.faMenuShortcutkey(groupBySubMenuSecondParentMenu.getShortcutkey())
											.faMenuTreelevel(groupBySubMenuSecondParentMenu.getTreelevel())
											.faFormName(groupBySubMenuSecondParentMenu.getFormname())
											.faMenuUsersup(passwdEntity.getUsersup())
											.faMenuTreelink(groupBySubMenuSecondParentMenu.getTreelink());
											if((MenuCodeToModuleRightMap != null && !MenuCodeToModuleRightMap.isEmpty()) && MenuCodeToModuleRightMap.containsKey(groupBySubMenuSecondParentMenu.getCode())) {
												faSubMenuSecondMenuResponseBean.rightsOptionsList(MenuCodeToModuleRightMap.get(groupBySubMenuSecondParentMenu.getCode()).stream().map(menuCodeToModuleRight -> accessRightsMap.containsKey(menuCodeToModuleRight.getModulerightCK().getMdrgRightsoptioncd().longValue()) ? accessRightsMap.get(menuCodeToModuleRight.getModulerightCK().getMdrgRightsoptioncd().longValue()) : null).collect(Collectors.toList()));
											}
											//TO SET FAVOURITES 
											if(CollectionUtils.isNotEmpty(userRightEntityList) && MenuCodeToModuleRightMap.containsKey(groupBySubMenuSecondParentMenu.getCode())) {
												MenuCodeToModuleRightMap.get(groupBySubMenuSecondParentMenu.getCode()).stream().map(menuCodeToModuleRight ->{
													userRightEntityList.stream().filter(f ->  f.getUserrightCK().getUsrtModulerightcode().equals(menuCodeToModuleRight.getMdrgCode())).map(userRightEntity -> {
														faSubMenuSecondMenuResponseBean.favourite("Y");
														return userRightEntity;
													}).collect(Collectors.toList());
													return menuCodeToModuleRight;
												}).collect(Collectors.toList());
											}
											//==========================================================================================================================(Third Level)=========================
											if((groupByParentSubLevelThreeMenuMap != null || !groupByParentSubLevelThreeMenuMap.isEmpty()) && groupByParentSubLevelThreeMenuMap.containsKey(groupBySubMenuSecondParentMenu.getNeumonic().substring(0, 3))) {
												faSubMenuSecondMenuResponseBean.faMenuResponseBeanList(
														groupByParentSubLevelThreeMenuMap.get(groupBySubMenuSecondParentMenu.getNeumonic().substring(0, 3)).stream().filter(faSubLevThreeMenuEntity -> faSubLevThreeMenuEntity.getTreelink() == 3).map(groupBySubMenuThirdParentMenu ->{
															FaMenuResponseBean.FaMenuResponseBeanBuilder faSubMenuThirdMenuResponseBean = FaMenuResponseBean.builder();

															faSubMenuThirdMenuResponseBean
															.faMenucode(groupBySubMenuThirdParentMenu.getCode())
															.faMenuDescription(groupBySubMenuThirdParentMenu.getDescription())
															.faMenuFlgactive(groupBySubMenuThirdParentMenu.getFlgactive())
															.faMenuMenuorder(groupBySubMenuThirdParentMenu.getMenuorder())
															.faMenuNeumonic(groupBySubMenuThirdParentMenu.getNeumonic())
															.faMenuSepgroup(groupBySubMenuThirdParentMenu.getSepgroup())
															.faMenuShortcutkey(groupBySubMenuThirdParentMenu.getShortcutkey())
															.faMenuTreelevel(groupBySubMenuThirdParentMenu.getTreelevel())
															.faFormName(groupBySubMenuThirdParentMenu.getFormname())
															.faMenuUsersup(passwdEntity.getUsersup())
															.faMenuTreelink(groupBySubMenuThirdParentMenu.getTreelink());
															if((MenuCodeToModuleRightMap != null && !MenuCodeToModuleRightMap.isEmpty()) && MenuCodeToModuleRightMap.containsKey(groupBySubMenuThirdParentMenu.getCode())) {
																faSubMenuThirdMenuResponseBean.rightsOptionsList(MenuCodeToModuleRightMap.get(groupBySubMenuThirdParentMenu.getCode()).stream().map(menuCodeToModuleRight -> accessRightsMap.containsKey(menuCodeToModuleRight.getModulerightCK().getMdrgRightsoptioncd().longValue()) ? accessRightsMap.get(menuCodeToModuleRight.getModulerightCK().getMdrgRightsoptioncd().longValue()) : null).collect(Collectors.toList()));
															}
															//TO SET FAVOURITES 
															if(CollectionUtils.isNotEmpty(userRightEntityList) && MenuCodeToModuleRightMap.containsKey(groupBySubMenuThirdParentMenu.getCode()) ) {
																MenuCodeToModuleRightMap.get(groupBySubMenuThirdParentMenu.getCode()).stream().map(menuCodeToModuleRight ->{
																	userRightEntityList.stream().filter(f ->  f.getUserrightCK().getUsrtModulerightcode().equals(menuCodeToModuleRight.getMdrgCode())).map(userRightEntity -> {
																		faSubMenuThirdMenuResponseBean.favourite("Y");
																		return userRightEntity;
																	}).collect(Collectors.toList());
																	return menuCodeToModuleRight;
																}).collect(Collectors.toList());
															}
															return faSubMenuThirdMenuResponseBean.build();
														}
																).collect(Collectors.toList())
														);
											}
											return faSubMenuSecondMenuResponseBean.build();
										}
												).collect(Collectors.toList())
										);
							}
							return faSubMenuFirstResponseBean.build();
						}
								).collect(Collectors.toList())
						);
			}
			return faMenuResponseBean.build();
		}).collect(Collectors.toList())).extraData(Objects.nonNull(passwdEntity) ? passwdEntity.getUsersup().trim() : null) .build());
	}

	@Override
	public ResponseEntity<?> updateFavourite(FavouriteRequestBeanList favouriteRequestBeanList) {
		Set<Integer> menuCodeToAdd = new HashSet<>();
		Set<Integer> menuCodeToRemove = new HashSet<>();

		Passwd passwdEntity = this.passwdRepository.findByAngularUserIgnoreCase(GenericAuditContextHolder.getContext().getUserid());
		logger.info("Passwd :: {}", passwdEntity);
		if(Objects.nonNull(passwdEntity)) {
			favouriteRequestBeanList.getFavouriteRequestBeanList().stream().map(favouriteRequestBean -> {
				if(favouriteRequestBean.getIsfavourite())
					menuCodeToAdd.add(favouriteRequestBean.getCode());
				else
				menuCodeToRemove.add(favouriteRequestBean.getCode());
				return favouriteRequestBean;
			}).collect(Collectors.toList());

			if(CollectionUtils.isNotEmpty(menuCodeToAdd))
				this.userRightsRepository.updatefavourite("Y", passwdEntity.getUserlog().trim(), menuCodeToAdd);

			if(CollectionUtils.isNotEmpty(menuCodeToRemove))
				this.userRightsRepository.updatefavourite(null, passwdEntity.getUserlog().trim(), menuCodeToRemove);

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Favourite Added Successfully").build());
		}
		else
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("User Not Found").build());
	}
}

