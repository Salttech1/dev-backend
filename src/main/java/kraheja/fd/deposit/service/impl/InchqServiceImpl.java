package kraheja.fd.deposit.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kraheja.commons.entity.Inchq;
import kraheja.commons.repository.InchqRepository;
import kraheja.fd.deposit.service.InchqService;

public class InchqServiceImpl implements InchqService{

	@Autowired
	private InchqRepository inchqRepository;
	
	@Override
	public List<Inchq> fetchInchqByInchqPartyCodeAndInchqRecNum(String partyCode, String recieptNo) {
		return inchqRepository.findByPartyCodeAndRecieptNum(partyCode, recieptNo);
	}
}
