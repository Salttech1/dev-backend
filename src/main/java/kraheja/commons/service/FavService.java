package kraheja.commons.service;

import java.util.Set;

import org.springframework.http.ResponseEntity;

public interface FavService {
	
	ResponseEntity<?> addFavs(Set<Integer> menucodes);
}
