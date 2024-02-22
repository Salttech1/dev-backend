package kraheja.commons.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.request.FavouriteRequestBeanList;
import kraheja.commons.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@PostMapping("/fetch-menu")
	public ResponseEntity<?> fetchMenu(@RequestBody Map<String, String> body) {
		return this.menuService.fetchMenu(body.get("username"));
	}
	
	@PutMapping("/update-favourite")
	public ResponseEntity<?> updateFavourite(@RequestBody FavouriteRequestBeanList favouriteRequestBeanList) {
		return this.menuService.updateFavourite(favouriteRequestBeanList);
	}
}
