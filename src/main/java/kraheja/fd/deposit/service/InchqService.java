package kraheja.fd.deposit.service;

import java.util.List;

import kraheja.commons.entity.Inchq;

public interface InchqService {

	List<Inchq> fetchInchqByInchqPartyCodeAndInchqRecNum(String partyCode, String recNum);
}
