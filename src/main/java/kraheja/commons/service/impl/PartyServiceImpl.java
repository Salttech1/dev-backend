package kraheja.commons.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.vehicleexp.dataentry.bean.response.VehicleInfoBean;
import kraheja.commons.bean.response.PartyNameGstResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Party;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.service.PartyService;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.CommonConstraints;

@Service
@Transactional
public class PartyServiceImpl implements PartyService{
	@Autowired
	private PartyRepository partyRepository;
	
	@Override
	public ResponseEntity<ServiceResponseBean> getPartyNameGstInfo(String  partycode, String  partytype) {
		Party partyNameGst = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partycode, CommonUtils.INSTANCE.closeDateInLocalDateTime(), partytype);
		if(Objects.isNull(partyNameGst)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Invalid Party Code").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(PartyNameGstResponseBean.builder()
				.partyname(partyNameGst.getParPartyname())
				.parGstno(partyNameGst.getParGstno()).build()).build());
//		return null;
	}
}
