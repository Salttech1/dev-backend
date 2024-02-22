package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.request.FavouriteRequestBeanList;

public interface MenuService {
	ResponseEntity<?> fetchMenu(String string);

	ResponseEntity<?> updateFavourite(FavouriteRequestBeanList favouriteRequestBeanList);
}
