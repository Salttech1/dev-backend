package kraheja.commons.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.service.FavService;

@RestController
@RequestMapping("/company")
public class FavController {
	
	@Autowired
	private FavService favService;
	
	@PutMapping("/add-favs")
	public ResponseEntity<?> addFavs(Set<Integer> menucodes){
		return this.favService.addFavs(menucodes);	
	}
	
}
