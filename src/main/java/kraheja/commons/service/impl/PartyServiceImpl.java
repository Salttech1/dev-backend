package kraheja.commons.service.impl;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import kraheja.commons.bean.request.PartyAdditionRequestBean;
import kraheja.commons.bean.request.PartyAddressRequestBean;
import kraheja.commons.bean.request.PartyRequestBean;
import kraheja.commons.bean.response.GenericResponse;
import kraheja.commons.bean.response.PartyNameGstResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Matcertlnvendoradr;
import kraheja.commons.entity.Party;
import kraheja.commons.entity.Partycontactinfo;
import kraheja.commons.entity.Partyenterpriseinfo;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.mappers.pojoentity.PartycontactinfoEntityPojoMapper;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.MatcertlnvendoradrRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.repository.PartycontactinfoRepository;
import kraheja.commons.repository.PartyenterpriseinfoRepository;
import kraheja.commons.service.PartyService;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;

@Service
@Transactional
public class PartyServiceImpl implements PartyService {
	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	PartycontactinfoRepository partycontactinfoRepository;

	@Autowired
	MatcertlnvendoradrRepository matcertlnvendoradrRepository;
	
	@Autowired
	AddressRepository addressRepository;

	@Autowired
	 EntityRepository entityRepository;
	
	@Autowired
	PartyenterpriseinfoRepository partyenterpriseinfoRepository;

	@Override
	public ResponseEntity<ServiceResponseBean> getPartyNameGstInfo(String partycode, String partytype) {
		Party partyNameGst = this.partyRepository
				.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partycode,
						CommonUtils.INSTANCE.closeDateInLocalDateTime(), partytype);
		if (Objects.isNull(partyNameGst)) {
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Invalid Party Code").build());
		}
		return ResponseEntity.ok(ServiceResponseBean
				.builder().status(Boolean.TRUE).data(PartyNameGstResponseBean.builder()
						.partyname(partyNameGst.getParPartyname()).parGstno(partyNameGst.getParGstno()).build())
				.build());
	}

	@Override
	public GenericResponse<Void> addParty(PartyAdditionRequestBean partyAdditionRequestBean) {
		if(Objects.isNull(partyAdditionRequestBean)) {
			 return new GenericResponse<>(false, "PartyAdditionRequest cannot be empty");
		}
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);

		Party party = PartyMapper.addPartyPojoEntityMapping.apply(new Object[] {partyAdditionRequestBean.getPartyRequestBean() ,siteFromDBEntity ,partyAdditionRequestBean.getPartyRequestBean().getPartycode()});

		partyRepository.save(party);

		if(Objects.isNull(partyAdditionRequestBean.getAddressRequestBean())) {
			return new GenericResponse<>(false, "Address cannot be empty");
		}
		Address address = AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {partyAdditionRequestBean.getAddressRequestBean() ,siteFromDBEntity ,partyAdditionRequestBean.getPartyRequestBean().getPartycode()}); 
		addressRepository.save(address);
	
		Partycontactinfo partycontactinfo = PartycontactinfoEntityPojoMapper.addPartycontactinfoPojoEntityMapper.apply(partyAdditionRequestBean.getPartycontactinfoRequestBean());
		
		return new GenericResponse<>(true, "Data saved successfully"); 
	}

	@Override
	public GenericResponse<HashMap<String, Object>> getPartyInfo(String partyType, String partyCode) {

		HashMap<String, Object> partyDetails = new HashMap<>();

		Party partyEntity = partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(
				partyCode, CommonUtils.INSTANCE.closeDateInLocalDateTime(), partyType);
		if (Objects.isNull(partyEntity)) {
			return new GenericResponse<>(false, "Invalid Party Code");
		}

		Partycontactinfo partyContactInfo = partycontactinfoRepository.findByPartycontactinfoCK_PciPartycode(partyCode);
		Address partyAddress = addressRepository.fetchByPartyCode(partyCode);
		Partyenterpriseinfo partyenterpriseinfo = partyenterpriseinfoRepository.findByPartyTypeandPartyCode(partyCode,partyType);
		
		Matcertlnvendoradr matcertlnvendoradr=null;
		
		if(Objects.isNull(partyContactInfo)) {
			matcertlnvendoradr = matcertlnvendoradrRepository.findByMcvaPartycodeAndMcvaPartytype(partyCode,partyType);
		}
		
		partyDetails.put("partyEntity", partyEntity);
		partyDetails.put("partyAddress", partyAddress);
		partyDetails.put("partyContactInfo",Objects.nonNull(partyContactInfo) ? partyContactInfo : matcertlnvendoradr );
		partyDetails.put("partyEnterpriseInfo", partyenterpriseinfo);

		return new GenericResponse<>(true, "Data fetched successfully", partyDetails);

	}

	@Override
	public GenericResponse<List<String>> getPartyName(String firstChar) {
		List<String> partyName = partyRepository.findByPartyCk_ParClosedate( firstChar.trim());

		if (Objects.isNull(partyName)) {
			return new GenericResponse<>(false, "Data not found.");
		}
		return new GenericResponse<>(true, "Data fetched successfully", partyName);

	}

}
