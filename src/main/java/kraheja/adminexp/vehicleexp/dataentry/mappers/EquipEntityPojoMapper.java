package kraheja.adminexp.vehicleexp.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import kraheja.commons.utils.CommonConstraints;
import kraheja.adminexp.vehicleexp.dataentry.bean.request.EquipRequestBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.EquipResponseBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.EquipResponseBean.EquipResponseBeanBuilder;
import kraheja.adminexp.vehicleexp.dataentry.entity.Equip;
import kraheja.adminexp.vehicleexp.dataentry.entity.EquipCK;

public interface EquipEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<EquipResponseBean>> fetchEquipEntityPojoMapper = objectArray -> {
		EquipResponseBeanBuilder equipBeanBuilder = EquipResponseBean.builder();
		List<Equip> equipEntityList = (List<Equip>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return equipEntityList.stream().map(equipEntity -> {
			equipBeanBuilder.eqptype(equipEntity.getEquipCK().getEqpEqptype())
					.eqpnum(equipEntity.getEquipCK().getEqpEqpnum())
					.allottedto(equipEntity.getEqpAllottedto())
					.authorisationvalidtill(Objects.nonNull(equipEntity.getEqpAuthorisationvalidtill()) ? equipEntity.getEqpAuthorisationvalidtill().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.avglast(equipEntity.getEqpAvglast())
					.batterychanged(Objects.nonNull(equipEntity.getEqpBatterychanged()) ? equipEntity.getEqpBatterychanged().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.batteryexpiry(Objects.nonNull(equipEntity.getEqpBatteryexpiry()) ? equipEntity.getEqpBatteryexpiry().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.bmctaxexp(Objects.nonNull(equipEntity.getEqpBmctaxexp()) ? equipEntity.getEqpBmctaxexp().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.bookvalue(equipEntity.getEqpBookvalue())
					.chasisno(equipEntity.getEqpChasisno())
					.coy(equipEntity.getEqpCoy())
					.dispaadhaar(equipEntity.getEqpDispaadhaar())
					.dispaddress(equipEntity.getEqpDispaddress())
					.dispchequedate(Objects.nonNull(equipEntity.getEqpDispchequedate()) ? equipEntity.getEqpDispchequedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.dispchequeno(equipEntity.getEqpDispchequeno())
					.dispcontactno(equipEntity.getEqpDispcontactno())
					.dispdate(Objects.nonNull(equipEntity.getEqpDispdate()) ? equipEntity.getEqpDispdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.dispname(equipEntity.getEqpDispname())
					.disppan(equipEntity.getEqpDisppan())
					.dispvalue(equipEntity.getEqpDispvalue())
					.emiamount(equipEntity.getEqpEmiamount())
					.emienddate(Objects.nonNull(equipEntity.getEqpEmienddate()) ? equipEntity.getEqpEmienddate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.emistartdate(Objects.nonNull(equipEntity.getEqpEmistartdate()) ? equipEntity.getEqpEmistartdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.enginecc(equipEntity.getEqpEnginecc())
					.engineno(equipEntity.getEqpEngineno())
					.fitnessvalidtill(Objects.nonNull(equipEntity.getEqpFitnessvalidtill()) ? equipEntity.getEqpFitnessvalidtill().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.hpacancelledon(Objects.nonNull(equipEntity.getEqpHpacancelledon()) ? equipEntity.getEqpHpacancelledon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.hpawith(equipEntity.getEqpHpawith())
					.inscompany(equipEntity.getEqpInscompany())
					.insexpon(Objects.nonNull(equipEntity.getEqpInsexpon()) ? equipEntity.getEqpInsexpon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.inspolicyno(equipEntity.getEqpInspolicyno())
					.kmslimit(equipEntity.getEqpKmslimit())
					.loanacno(equipEntity.getEqpLoanacno())
					.loanamount(equipEntity.getEqpLoanamount())
					.loanpapersignedby(equipEntity.getEqpLoanpapersignedby())
					.lymexp(equipEntity.getEqpLymexp())
					.lyrexp(equipEntity.getEqpLyrexp())
					.nextmaint(Objects.nonNull(equipEntity.getEqpNextmaint()) ? equipEntity.getEqpNextmaint().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.origsite(equipEntity.getEqpOrigsite())
					.permitvalidtill(Objects.nonNull(equipEntity.getEqpPermitvalidtill()) ? equipEntity.getEqpPermitvalidtill().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.prop(equipEntity.getEqpProp())
					.purcdate(Objects.nonNull(equipEntity.getEqpPurcdate()) ? equipEntity.getEqpPurcdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.rcbooksignedby(equipEntity.getEqpRcbooksignedby())
					.registrationauth(equipEntity.getEqpRegistrationauth())
					.regvalidtill(Objects.nonNull(equipEntity.getEqpRegvalidtill()) ? equipEntity.getEqpRegvalidtill().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.rtotaxexp(equipEntity.getEqpRtotaxexp())
					.rtotaxpaidtill(Objects.nonNull(equipEntity.getEqpRtotaxpaidtill()) ? equipEntity.getEqpRtotaxpaidtill().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.runon(equipEntity.getEqpRunon())
					.servexpiry(Objects.nonNull(equipEntity.getEqpServexpiry()) ? equipEntity.getEqpServexpiry().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.servicedone(Objects.nonNull(equipEntity.getEqpServicedone()) ? equipEntity.getEqpServicedone().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.servstatus(equipEntity.getEqpServstatus())
					.site(equipEntity.getEqpSite())
					.sitebldg(equipEntity.getEqpSitebldg())
					.todatemexp(equipEntity.getEqpTodatemexp())
					.todaterexp(equipEntity.getEqpTodaterexp())
					.today(equipEntity.getEqpToday())
					.tuneexpiry(Objects.nonNull(equipEntity.getEqpTuneexpiry()) ? equipEntity.getEqpTuneexpiry().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.tuneup(Objects.nonNull(equipEntity.getEqpTuneup()) ? equipEntity.getEqpTuneup().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.tymexp(equipEntity.getEqpTymexp())
					.tyrexp(equipEntity.getEqpTyrexp())
					.userid(equipEntity.getEqpUserid())
					.vehmake(equipEntity.getEqpVehmake())
					.vehtype(equipEntity.getEqpVehtype())
;
			return equipBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Equip> addEquipEntityPojoMapper = objectArray -> {
		EquipRequestBean equipRequestBean = (EquipRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);

		Equip.		EquipBuilder equipbuilder = Equip.builder();

equipbuilder
			.equipCK(EquipCK.builder()
					.eqpEqptype(equipRequestBean.getEqptype())
					.eqpEqpnum(equipRequestBean.getEqpnum())
		.build())
					.eqpAllottedto(equipRequestBean.getAllottedto())
					.eqpAuthorisationvalidtill(Objects.nonNull(equipRequestBean.getAuthorisationvalidtill()) ? LocalDate.parse(equipRequestBean.getAuthorisationvalidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpAvglast(equipRequestBean.getAvglast())
					.eqpBatterychanged(Objects.nonNull(equipRequestBean.getBatterychanged()) ? LocalDate.parse(equipRequestBean.getBatterychanged(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpBatteryexpiry(Objects.nonNull(equipRequestBean.getBatteryexpiry()) ? LocalDate.parse(equipRequestBean.getBatteryexpiry(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpBmctaxexp(Objects.nonNull(equipRequestBean.getBmctaxexp()) ? LocalDate.parse(equipRequestBean.getBmctaxexp(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpBookvalue(equipRequestBean.getBookvalue())
					.eqpChasisno(equipRequestBean.getChasisno())
					.eqpCoy(equipRequestBean.getCoy())
					.eqpDispaadhaar(equipRequestBean.getDispaadhaar())
					.eqpDispaddress(equipRequestBean.getDispaddress())
					.eqpDispchequedate(Objects.nonNull(equipRequestBean.getDispchequedate()) ? LocalDate.parse(equipRequestBean.getDispchequedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpDispchequeno(equipRequestBean.getDispchequeno())
					.eqpDispcontactno(equipRequestBean.getDispcontactno())
					.eqpDispdate(Objects.nonNull(equipRequestBean.getDispdate()) ? LocalDate.parse(equipRequestBean.getDispdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpDispname(equipRequestBean.getDispname())
					.eqpDisppan(equipRequestBean.getDisppan())
					.eqpDispvalue(equipRequestBean.getDispvalue())
					.eqpEmiamount(equipRequestBean.getEmiamount())
					.eqpEmienddate(Objects.nonNull(equipRequestBean.getEmienddate()) ? LocalDate.parse(equipRequestBean.getEmienddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpEmistartdate(Objects.nonNull(equipRequestBean.getEmistartdate()) ? LocalDate.parse(equipRequestBean.getEmistartdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpEnginecc(equipRequestBean.getEnginecc())
					.eqpEngineno(equipRequestBean.getEngineno())
					.eqpFitnessvalidtill(Objects.nonNull(equipRequestBean.getFitnessvalidtill()) ? LocalDate.parse(equipRequestBean.getFitnessvalidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpHpacancelledon(Objects.nonNull(equipRequestBean.getHpacancelledon()) ? LocalDate.parse(equipRequestBean.getHpacancelledon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpHpawith(equipRequestBean.getHpawith())
					.eqpInscompany(equipRequestBean.getInscompany())
					.eqpInsexpon(Objects.nonNull(equipRequestBean.getInsexpon()) ? LocalDate.parse(equipRequestBean.getInsexpon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpInspolicyno(equipRequestBean.getInspolicyno())
					.eqpKmslimit(equipRequestBean.getKmslimit())
					.eqpLoanacno(equipRequestBean.getLoanacno())
					.eqpLoanamount(equipRequestBean.getLoanamount())
					.eqpLoanpapersignedby(equipRequestBean.getLoanpapersignedby())
					.eqpLymexp(equipRequestBean.getLymexp())
					.eqpLyrexp(equipRequestBean.getLyrexp())
					.eqpNextmaint(Objects.nonNull(equipRequestBean.getNextmaint()) ? LocalDate.parse(equipRequestBean.getNextmaint(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpOrigsite(equipRequestBean.getOrigsite())
					.eqpPermitvalidtill(Objects.nonNull(equipRequestBean.getPermitvalidtill()) ? LocalDate.parse(equipRequestBean.getPermitvalidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpProp(equipRequestBean.getProp())
					.eqpPurcdate(Objects.nonNull(equipRequestBean.getPurcdate()) ? LocalDate.parse(equipRequestBean.getPurcdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpRcbooksignedby(equipRequestBean.getRcbooksignedby())
					.eqpRegistrationauth(equipRequestBean.getRegistrationauth())
					.eqpRegvalidtill(Objects.nonNull(equipRequestBean.getRegvalidtill()) ? LocalDate.parse(equipRequestBean.getRegvalidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpRtotaxexp(equipRequestBean.getRtotaxexp())
					.eqpRtotaxpaidtill(Objects.nonNull(equipRequestBean.getRtotaxpaidtill()) ? LocalDate.parse(equipRequestBean.getRtotaxpaidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpRunon(equipRequestBean.getRunon())
					.eqpServexpiry(Objects.nonNull(equipRequestBean.getServexpiry()) ? LocalDate.parse(equipRequestBean.getServexpiry(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpServicedone(Objects.nonNull(equipRequestBean.getServicedone()) ? LocalDate.parse(equipRequestBean.getServicedone(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpServstatus(equipRequestBean.getServstatus())
					.eqpSite(site)
					.eqpSitebldg(equipRequestBean.getSitebldg())
					.eqpTodatemexp(equipRequestBean.getTodatemexp())
					.eqpTodaterexp(equipRequestBean.getTodaterexp())
					.eqpToday(LocalDateTime.now())
					.eqpTuneexpiry(Objects.nonNull(equipRequestBean.getTuneexpiry()) ? LocalDate.parse(equipRequestBean.getTuneexpiry(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpTuneup(Objects.nonNull(equipRequestBean.getTuneup()) ? LocalDate.parse(equipRequestBean.getTuneup(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eqpTymexp(equipRequestBean.getTymexp())
					.eqpTyrexp(equipRequestBean.getTyrexp())
					.eqpUserid(equipRequestBean.getUserid())
					.eqpVehmake(equipRequestBean.getVehmake())
					.eqpVehtype(equipRequestBean.getVehtype())
		;

		return equipbuilder.build();
};

	public static BiFunction<Equip, EquipRequestBean, Equip> updateEquipEntityPojoMapper = (equipEntity, equipRequestBean) -> {
		equipEntity.getEquipCK().setEqpEqptype(StringUtils.isNotBlank(equipRequestBean.getEqptype()) ? equipRequestBean.getEqptype().trim() : equipEntity.getEquipCK().getEqpEqptype());
		equipEntity.getEquipCK().setEqpEqpnum(StringUtils.isNotBlank(equipRequestBean.getEqpnum()) ? equipRequestBean.getEqpnum().trim() : equipEntity.getEquipCK().getEqpEqpnum());
		equipEntity.setEqpAllottedto(StringUtils.isNotBlank(equipRequestBean.getAllottedto()) ? equipRequestBean.getAllottedto().trim() : equipEntity.getEqpAllottedto());
		equipEntity.setEqpAuthorisationvalidtill(Objects.nonNull(equipRequestBean.getAuthorisationvalidtill()) ? LocalDate.parse(equipRequestBean.getAuthorisationvalidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpAuthorisationvalidtill());
		equipEntity.setEqpAvglast(Objects.nonNull(equipRequestBean.getAvglast())? equipRequestBean.getAvglast() : equipEntity.getEqpAvglast());		
		equipEntity.setEqpBatterychanged(Objects.nonNull(equipRequestBean.getBatterychanged()) ? LocalDate.parse(equipRequestBean.getBatterychanged(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpBatterychanged());
		equipEntity.setEqpBatteryexpiry(Objects.nonNull(equipRequestBean.getBatteryexpiry()) ? LocalDate.parse(equipRequestBean.getBatteryexpiry(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpBatteryexpiry());
		equipEntity.setEqpBmctaxexp(Objects.nonNull(equipRequestBean.getBmctaxexp()) ? LocalDate.parse(equipRequestBean.getBmctaxexp(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpBmctaxexp());
		equipEntity.setEqpBookvalue(Objects.nonNull(equipRequestBean.getBookvalue()) ? equipRequestBean.getBookvalue() : equipEntity.getEqpBookvalue());		
		equipEntity.setEqpChasisno(StringUtils.isNotBlank(equipRequestBean.getChasisno()) ? equipRequestBean.getChasisno().trim() : equipEntity.getEqpChasisno());
		equipEntity.setEqpCoy(StringUtils.isNotBlank(equipRequestBean.getCoy()) ? equipRequestBean.getCoy().trim() : equipEntity.getEqpCoy());
		equipEntity.setEqpDispaadhaar(StringUtils.isNotBlank(equipRequestBean.getDispaadhaar()) ? equipRequestBean.getDispaadhaar().trim() : equipEntity.getEqpDispaadhaar());
		equipEntity.setEqpDispaddress(StringUtils.isNotBlank(equipRequestBean.getDispaddress()) ? equipRequestBean.getDispaddress().trim() : equipEntity.getEqpDispaddress());
		equipEntity.setEqpDispchequedate(Objects.nonNull(equipRequestBean.getDispchequedate()) ? LocalDate.parse(equipRequestBean.getDispchequedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpDispchequedate());
		equipEntity.setEqpDispchequeno(StringUtils.isNotBlank(equipRequestBean.getDispchequeno()) ? equipRequestBean.getDispchequeno().trim() : equipEntity.getEqpDispchequeno());
		equipEntity.setEqpDispcontactno(StringUtils.isNotBlank(equipRequestBean.getDispcontactno()) ? equipRequestBean.getDispcontactno().trim() : equipEntity.getEqpDispcontactno());
		equipEntity.setEqpDispdate(Objects.nonNull(equipRequestBean.getDispdate()) ? LocalDate.parse(equipRequestBean.getDispdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpDispdate());
		equipEntity.setEqpDispname(StringUtils.isNotBlank(equipRequestBean.getDispname()) ? equipRequestBean.getDispname().trim() : equipEntity.getEqpDispname());
		equipEntity.setEqpDisppan(StringUtils.isNotBlank(equipRequestBean.getDisppan()) ? equipRequestBean.getDisppan().trim() : equipEntity.getEqpDisppan());
		equipEntity.setEqpDispvalue(Objects.nonNull(equipRequestBean.getDispvalue()) ? equipRequestBean.getDispvalue() : equipEntity.getEqpDispvalue());		
		equipEntity.setEqpEmiamount(Objects.nonNull(equipRequestBean.getEmiamount()) ? equipRequestBean.getEmiamount() : equipEntity.getEqpEmiamount());		
		equipEntity.setEqpEmienddate(Objects.nonNull(equipRequestBean.getEmienddate()) ? LocalDate.parse(equipRequestBean.getEmienddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpEmienddate());
		equipEntity.setEqpEmistartdate(Objects.nonNull(equipRequestBean.getEmistartdate()) ? LocalDate.parse(equipRequestBean.getEmistartdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpEmistartdate());
		equipEntity.setEqpEnginecc(StringUtils.isNotBlank(equipRequestBean.getEnginecc()) ? equipRequestBean.getEnginecc().trim() : equipEntity.getEqpEnginecc());
		equipEntity.setEqpEngineno(StringUtils.isNotBlank(equipRequestBean.getEngineno()) ? equipRequestBean.getEngineno().trim() : equipEntity.getEqpEngineno());
		equipEntity.setEqpFitnessvalidtill(Objects.nonNull(equipRequestBean.getFitnessvalidtill()) ? LocalDate.parse(equipRequestBean.getFitnessvalidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpFitnessvalidtill());
		equipEntity.setEqpHpacancelledon(Objects.nonNull(equipRequestBean.getHpacancelledon()) ? LocalDate.parse(equipRequestBean.getHpacancelledon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpHpacancelledon());
		equipEntity.setEqpHpawith(StringUtils.isNotBlank(equipRequestBean.getHpawith()) ? equipRequestBean.getHpawith().trim() : equipEntity.getEqpHpawith());
		equipEntity.setEqpInscompany(StringUtils.isNotBlank(equipRequestBean.getInscompany()) ? equipRequestBean.getInscompany().trim() : equipEntity.getEqpInscompany());
		equipEntity.setEqpInsexpon(Objects.nonNull(equipRequestBean.getInsexpon()) ? LocalDate.parse(equipRequestBean.getInsexpon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpInsexpon());
		equipEntity.setEqpInspolicyno(StringUtils.isNotBlank(equipRequestBean.getInspolicyno()) ? equipRequestBean.getInspolicyno().trim() : equipEntity.getEqpInspolicyno());
		equipEntity.setEqpKmslimit(Objects.nonNull(equipRequestBean.getKmslimit()) ? equipRequestBean.getKmslimit() : equipEntity.getEqpKmslimit());		
		equipEntity.setEqpLoanacno(StringUtils.isNotBlank(equipRequestBean.getLoanacno()) ? equipRequestBean.getLoanacno().trim() : equipEntity.getEqpLoanacno());
		equipEntity.setEqpLoanamount(Objects.nonNull(equipRequestBean.getLoanamount()) ? equipRequestBean.getLoanamount() : equipEntity.getEqpLoanamount());		
		equipEntity.setEqpLoanpapersignedby(StringUtils.isNotBlank(equipRequestBean.getLoanpapersignedby()) ? equipRequestBean.getLoanpapersignedby().trim() : equipEntity.getEqpLoanpapersignedby());
		equipEntity.setEqpLymexp(Objects.nonNull(equipRequestBean.getLymexp()) ? equipRequestBean.getLymexp() : equipEntity.getEqpLymexp());		
		equipEntity.setEqpLyrexp(Objects.nonNull(equipRequestBean.getLyrexp()) ? equipRequestBean.getLyrexp() : equipEntity.getEqpLyrexp());		
		equipEntity.setEqpNextmaint(Objects.nonNull(equipRequestBean.getNextmaint()) ? LocalDate.parse(equipRequestBean.getNextmaint(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpNextmaint());
		equipEntity.setEqpOrigsite(StringUtils.isNotBlank(equipRequestBean.getOrigsite()) ? equipRequestBean.getOrigsite().trim() : equipEntity.getEqpOrigsite());
		equipEntity.setEqpPermitvalidtill(Objects.nonNull(equipRequestBean.getPermitvalidtill()) ? LocalDate.parse(equipRequestBean.getPermitvalidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpPermitvalidtill());
		equipEntity.setEqpProp(StringUtils.isNotBlank(equipRequestBean.getProp()) ? equipRequestBean.getProp().trim() : equipEntity.getEqpProp());
		equipEntity.setEqpPurcdate(Objects.nonNull(equipRequestBean.getPurcdate()) ? LocalDate.parse(equipRequestBean.getPurcdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpPurcdate());
		equipEntity.setEqpRcbooksignedby(StringUtils.isNotBlank(equipRequestBean.getRcbooksignedby()) ? equipRequestBean.getRcbooksignedby().trim() : equipEntity.getEqpRcbooksignedby());
		equipEntity.setEqpRegistrationauth(StringUtils.isNotBlank(equipRequestBean.getRegistrationauth()) ? equipRequestBean.getRegistrationauth().trim() : equipEntity.getEqpRegistrationauth());
		equipEntity.setEqpRegvalidtill(Objects.nonNull(equipRequestBean.getRegvalidtill()) ? LocalDate.parse(equipRequestBean.getRegvalidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpRegvalidtill());
		equipEntity.setEqpRtotaxexp(StringUtils.isNotBlank(equipRequestBean.getRtotaxexp()) ? equipRequestBean.getRtotaxexp().trim() : equipEntity.getEqpRtotaxexp());
		equipEntity.setEqpRtotaxpaidtill(Objects.nonNull(equipRequestBean.getRtotaxpaidtill()) ? LocalDate.parse(equipRequestBean.getRtotaxpaidtill(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpRtotaxpaidtill());
		equipEntity.setEqpRunon(StringUtils.isNotBlank(equipRequestBean.getRunon()) ? equipRequestBean.getRunon().trim() : equipEntity.getEqpRunon());
		equipEntity.setEqpServexpiry(Objects.nonNull(equipRequestBean.getServexpiry()) ? LocalDate.parse(equipRequestBean.getServexpiry(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpServexpiry());
		equipEntity.setEqpServicedone(Objects.nonNull(equipRequestBean.getServicedone()) ? LocalDate.parse(equipRequestBean.getServicedone(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpServicedone());
		equipEntity.setEqpServstatus(StringUtils.isNotBlank(equipRequestBean.getServstatus()) ? equipRequestBean.getServstatus().trim() : equipEntity.getEqpServstatus());
		equipEntity.setEqpSite(StringUtils.isNotBlank(equipRequestBean.getSite()) ? equipRequestBean.getSite().trim() : equipEntity.getEqpSite());
		equipEntity.setEqpSitebldg(StringUtils.isNotBlank(equipRequestBean.getSitebldg()) ? equipRequestBean.getSitebldg().trim() : equipEntity.getEqpSitebldg());
		equipEntity.setEqpTodatemexp(Objects.nonNull(equipRequestBean.getTodatemexp()) ? equipRequestBean.getTodatemexp() : equipEntity.getEqpTodatemexp());		
		equipEntity.setEqpTodaterexp(Objects.nonNull(equipRequestBean.getTodaterexp()) ? equipRequestBean.getTodaterexp() : equipEntity.getEqpTodaterexp());		
		equipEntity.setEqpToday(LocalDateTime.now());
		equipEntity.setEqpTuneexpiry(Objects.nonNull(equipRequestBean.getTuneexpiry()) ? LocalDate.parse(equipRequestBean.getTuneexpiry(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpTuneexpiry());
		equipEntity.setEqpTuneup(Objects.nonNull(equipRequestBean.getTuneup()) ? LocalDate.parse(equipRequestBean.getTuneup(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : equipEntity.getEqpTuneup());
		equipEntity.setEqpTymexp(Objects.nonNull(equipRequestBean.getTymexp()) ? equipRequestBean.getTymexp() : equipEntity.getEqpTymexp());		
		equipEntity.setEqpTyrexp(Objects.nonNull(equipRequestBean.getTyrexp()) ? equipRequestBean.getTyrexp() : equipEntity.getEqpTyrexp());		
		equipEntity.setEqpUserid(StringUtils.isNotBlank(equipRequestBean.getUserid()) ? equipRequestBean.getUserid().trim() : equipEntity.getEqpUserid());
		equipEntity.setEqpVehmake(StringUtils.isNotBlank(equipRequestBean.getVehmake()) ? equipRequestBean.getVehmake().trim() : equipEntity.getEqpVehmake());
		equipEntity.setEqpVehtype(StringUtils.isNotBlank(equipRequestBean.getVehtype()) ? equipRequestBean.getVehtype().trim() : equipEntity.getEqpVehtype());


		return equipEntity;
	};

}