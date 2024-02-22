package kraheja.commons.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kraheja.commons.bean.request.PartyRequestBean;
import kraheja.commons.entity.Party;
import kraheja.commons.entity.PartyCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.repository.PartyRepository;

@Component
public class UpdatePartyUtility {

	@Autowired
	PartyRepository partyRepository;
	
	/* ==============================================================================
	 * This method will update the exisiting party and set it's close date, and add
	 * new row with close date and updated partyname. Or if partyname is not changed
	 * then it will simply update all the other columns.
	 * ==============================================================================
	 */
	public void updateParty(Party partyEntity, PartyRequestBean partyRequestBean) {
		if(!partyEntity.getParPartyname().equals(partyRequestBean.getPartyname().trim())) {
			
			Party oldPartyEntity  = Party.builder()
			.partyCk(PartyCK.builder()
					.parPartycode(partyEntity.getPartyCk().getParPartycode())
					.parPartytype(partyEntity.getPartyCk().getParPartytype())
					.parClosedate(LocalDate.now().minusDays(1).atStartOfDay()) 
					.build())
			.parTitle(Objects.nonNull(partyEntity.getParTitle()) ? partyEntity.getParTitle().trim() : null)
			.parPartyname(Objects.nonNull(partyEntity.getParPartyname()) ? partyEntity.getParPartyname().trim() : null)
			.parPartyconstt(Objects.nonNull(partyEntity.getParPartyconstt()) ? partyEntity.getParPartyconstt().trim() : null)
			.parCity(Objects.nonNull(partyEntity.getParCity()) ? partyEntity.getParCity().trim() : null)
			.parOpendate(partyEntity.getParOpendate())
			.parPmtacnum(Objects.nonNull(partyEntity.getParPmtacnum()) ? partyEntity.getParPmtacnum().trim() : null)
			.parStaxregncen(Objects.nonNull(partyEntity.getParStaxregncen()) ? partyEntity.getParStaxregncen().trim() : null)
			.parStaxregnst(Objects.nonNull(partyEntity.getParStaxregnst()) ? partyEntity.getParStaxregnst().trim() : null)
			.parOStaxregncen(Objects.nonNull(partyEntity.getParOStaxregncen()) ? partyEntity.getParOStaxregncen().trim() : null)
			.parOStaxregnst(Objects.nonNull(partyEntity.getParOStaxregnst()) ? partyEntity.getParOStaxregnst().trim() : null)
			.parRcnum(Objects.nonNull(partyEntity.getParRcnum()) ? partyEntity.getParRcnum().trim() : null)
			.parValidparty(partyEntity.getParValidparty())
			.parValidminor(partyEntity.getParValidminor())
			.parSite(GenericAuditContextHolder.getContext().getSite())
			.parUserid(GenericAuditContextHolder.getContext().getUserid())
			.parToday(LocalDateTime.now())
			.parSupptype(Objects.nonNull(partyEntity.getParSupptype()) ? partyEntity.getParSupptype().trim() : null)
			.parLtdcoyn(Objects.nonNull(partyEntity.getParLtdcoyn()) ? partyEntity.getParLtdcoyn().trim() : null)
			.parLastintpaid(Objects.nonNull(partyEntity.getParLastintpaid()) ?  partyEntity.getParLastintpaid() : null)
			.parVateffectivefrom(Objects.nonNull(partyEntity.getParVateffectivefrom()) ?  partyEntity.getParVateffectivefrom() : null)
			.parVatnum(Objects.nonNull(partyEntity.getParVatnum()) ? partyEntity.getParVatnum().trim() : null)
			.parTinnum(Objects.nonNull(partyEntity.getParTinnum()) ? partyEntity.getParTinnum().trim() : null)
			.parServicetaxnum(Objects.nonNull(partyEntity.getParServicetaxnum()) ? partyEntity.getParServicetaxnum().trim() : null)
			.parGstno(Objects.nonNull(partyEntity.getParGstno()) ? partyEntity.getParGstno().trim() : null)
			.parPayeebankcode1(Objects.nonNull(partyEntity.getParPayeebankcode1()) ? partyEntity.getParPayeebankcode1().trim() : null)
			.parPayeebranch1(Objects.nonNull(partyEntity.getParPayeebranch1()) ? partyEntity.getParPayeebranch1().trim() : null)
			.parPayeeacnum1(Objects.nonNull(partyEntity.getParPayeeacnum1()) ? partyEntity.getParPayeeacnum1().trim() : null)
			.parPayeeifsc1(Objects.nonNull(partyEntity.getParPayeeifsc1()) ? partyEntity.getParPayeeifsc1().trim() : null)
			.parPayeebankcode2(Objects.nonNull(partyEntity.getParPayeebankcode2()) ? partyEntity.getParPayeebankcode2().trim() : null)
			.parPayeebranch2(Objects.nonNull(partyEntity.getParPayeebranch2()) ? partyEntity.getParPayeebranch2().trim() : null)
			.parPayeeacnum2(Objects.nonNull(partyEntity.getParPayeeacnum2()) ? partyEntity.getParPayeeacnum2().trim() : null)
			.parPayeeifsc2(Objects.nonNull(partyEntity.getParPayeeifsc2()) ? partyEntity.getParPayeeifsc2().trim() : null)
			.parTanno(Objects.nonNull(partyEntity.getParTanno()) ? partyEntity.getParTanno().trim() : null)
			.parProfessiontaxno(Objects.nonNull(partyEntity.getParProfessiontaxno()) ? partyEntity.getParProfessiontaxno().trim() : null)
			.parPfno(Objects.nonNull(partyEntity.getParPfno()) ? partyEntity.getParPfno().trim() : null)
			.parEsicno(Objects.nonNull(partyEntity.getParEsicno()) ? partyEntity.getParEsicno().trim() : null)
			.parCino(Objects.nonNull(partyEntity.getParCino()) ? partyEntity.getParCino().trim() : null)
			.parAadharno(Objects.nonNull(partyEntity.getParAadharno()) ? partyEntity.getParAadharno().trim() : null)
			.build();
			
			this.partyRepository.deleteById(partyEntity.getPartyCk());
			
			//insert old record
			this.partyRepository.save(oldPartyEntity);
			
			//insert new record
			partyRequestBean.setOpendate(new Date());
			partyRequestBean.setClosedate(CommonUtils.INSTANCE.closeDate());
			this.partyRepository.save(PartyMapper.addPartyPojoEntityMapping.apply(new Object[] {partyRequestBean, GenericAuditContextHolder.getContext().getSite(), partyEntity.getPartyCk().getParPartycode().trim()}));
			
		}else				
			this.partyRepository.save(PartyMapper.updatePartyEntityPojoMapper.apply(partyEntity, partyRequestBean));
	
	}
}
