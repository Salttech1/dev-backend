package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

public interface GstService {

	ResponseEntity<?> fetchGstPerc(String hsncode, String partycode, String adowner, String segment, String adpartytype, String adtype, String suppbilldt,String adrPartySegment, String ser, Boolean isUpdate);
}
