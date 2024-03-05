// 29.02.24 RS  -  FA Common APIs

package kraheja.fa.commons.service;

import org.springframework.http.ResponseEntity;

public interface FaCommonsService {

	ResponseEntity<?> fetchMergedCoy(String coy, String mergeDate);

}
