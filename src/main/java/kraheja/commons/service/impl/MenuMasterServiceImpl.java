//package kraheja.commons.service.impl;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import kraheja.commons.bean.response.MenuMasterResponseBean;
//import kraheja.commons.bean.response.ServiceResponseBean;
//import kraheja.commons.repository.MenuMasterRepository;
//import kraheja.commons.service.MenuMasterService;
//import kraheja.fd.deposit.bean.request.MenuRequestBean;
//
//@Service
//public class MenuMasterServiceImpl implements MenuMasterService {
//
//	private final MenuMasterRepository menuMasterRepository;
//
//	@Autowired
//	public MenuMasterServiceImpl(MenuMasterRepository menuMasterRepository) {
//		this.menuMasterRepository = menuMasterRepository;
//	}
//
//	@Override
//	public ResponseEntity<?> fetchMenu() {
//		List<MenuMasterResponseBean> menuMasterList = this.menuMasterRepository.findMenu();
//		Map<String, List<MenuMasterResponseBean>> map1 = menuMasterList.stream().collect(Collectors.groupingBy(MenuMasterResponseBean::getLev1));
//			Map<String, List<MenuMasterResponseBean>> map2 = map1.get("FD").stream().collect(Collectors.groupingBy(MenuMasterResponseBean::getLev2));
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(menuMasterList).errorCode(HttpStatus.OK.value()).build());
//	}
//
//	@Override
//	public ResponseEntity<?> addMenu(MenuRequestBean menuRequestBean) {
//		return null;
//	}
//
//	@Override
//	public ResponseEntity<?> updateMenu(MenuRequestBean menuRequestBean) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
