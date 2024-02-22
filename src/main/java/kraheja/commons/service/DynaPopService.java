package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

public interface DynaPopService {

	ResponseEntity<?> dynaPop(String dynaPopId, String queryConditon);

	ResponseEntity<?> dynaPopFindBySearchText(String dynaPopId, String queryConditon, String searchText);
}
