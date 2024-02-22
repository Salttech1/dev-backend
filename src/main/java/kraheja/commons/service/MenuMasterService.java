package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.request.MenuRequestBean;

public interface MenuMasterService {
	public ResponseEntity<?> fetchMenu();
	
	public ResponseEntity<?> addMenu(MenuRequestBean menuRequestBean);
	
	public ResponseEntity<?> updateMenu(MenuRequestBean menuRequestBean);
}
