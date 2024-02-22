package kraheja.commons.mappers.pojoentity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.request.InchqRequestBean;
import kraheja.commons.bean.request.OutchqRequestBean;
import kraheja.commons.bean.request.ReportMasterRequestBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.ActrandCK;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.ActranhCK;
import kraheja.commons.entity.Inchq;
import kraheja.commons.entity.InchqCK;
import kraheja.commons.entity.Outchq;
import kraheja.commons.entity.OutchqCK;
import kraheja.commons.entity.ReportMaster;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;

public interface AddPojoEntityMapper {

	final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static Function<ReportMasterRequestBean, ReportMaster> addReportPojoEntityMapping = reportMasterRequestBean -> 
	ReportMaster.builder()
	.exportType(reportMasterRequestBean.getExportType())
	.name(reportMasterRequestBean.getName())
	.reportMetaData(reportMasterRequestBean.getReportMetaData())
	.reportType(reportMasterRequestBean.getReportType())
	.rptPath(reportMasterRequestBean.getRptPath())
	.build();

	@SuppressWarnings("unchecked")
	public static Function<Object[], List<Inchq>> addInchqPojoEntityMappingList = objectArray -> {
		List<InchqRequestBean> inchqRequestBeanList = (List<InchqRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);

		return inchqRequestBeanList.stream().map(inchqRequestBean -> {
			Inchq.InchqBuilder inchqBuilder = Inchq.builder();
			inchqBuilder 
			.inchqCk(InchqCK.builder().inchqNum(inchqRequestBean.getNum())
					.inchqBank(inchqRequestBean.getBank())
					.inchqCoy(inchqRequestBean.getCoy())
					.inchqRecnum(inchqRequestBean.getRecnum())
					.inchqTranser(inchqRequestBean.getTranser()).build())
			.inchqPaymode(inchqRequestBean.getPaymode())
			.inchqAmount(inchqRequestBean.getAmount())
			.inchqDate(StringUtils.isNotBlank(inchqRequestBean.getChqDate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(inchqRequestBean.getChqDate()) : null)
			.inchqSlipnum(inchqRequestBean.getSlipnum())
			.inchqOutstat(inchqRequestBean.getOutstat())
			.inchqProprietor(inchqRequestBean.getProprietor())
			.inchqOrigsys(inchqRequestBean.getOrigsys())
			.inchqPartycode(inchqRequestBean.getPartycode())
			.inchqFund(inchqRequestBean.getFund())
			.inchqActype(inchqRequestBean.getActype())
			.inchqLoanyn(inchqRequestBean.getLoanyn())
			.inchqSite(site)
			.inchqUserid(inchqRequestBean.getUserid())
			.inchqToday(LocalDateTime.now())
			.inchqOrigsite(site)
			.inchqCoybank(inchqRequestBean.getCoyBank());
			return inchqBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Actranh> addActranhPojoEntityMapping = objectArray -> {
		ActranhBean actranhBean = (ActranhBean) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);

		return Actranh.builder()
				.actranhCK(ActranhCK.builder()
						.acthTranser(actranhBean.getTranser())
						.acthCoy(actranhBean.getCoy())
						.build())
				.acthTrantype(actranhBean.getTrantype())
				.acthTrandate(StringUtils.isNotBlank(actranhBean.getTrandate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(actranhBean.getTrandate()) : null)
				.acthVounum(actranhBean.getVounum())
				.acthProprietor(actranhBean.getProprietor())
				.acthCoytranser(actranhBean.getCoytranser())
				.acthLedgcode(actranhBean.getLedgcode())//hardcode in setter in service impl
				.acthPartytype(actranhBean.getPartytype())
				.acthPartycode(actranhBean.getPartycode())//concat 
				.acthTranamt(actranhBean.getTranamt())
				.acthVoudate(StringUtils.isNotBlank(actranhBean.getVoudate()) ? LocalDate.parse(actranhBean.getVoudate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.acthBankcode(actranhBean.getBankcode())
				.acthPostedyn(actranhBean.getPostedyn())
				.acthBalancedyn(actranhBean.getBalancedyn())
				.acthClosingjvyn(actranhBean.getClosingjvyn())
				.acthNarrative(actranhBean.getNarrative())
				.acthBbookyn(actranhBean.getBbookyn())
				.acthCbookyn(actranhBean.getCbookyn())
				.acthSite(actranhBean.getSite())
				.acthUserid(actranhBean.getUserid())
				.acthToday(LocalDateTime.now())
				.acthReverseyn(actranhBean.getReverseyn())
				.acthClearacyn(actranhBean.getClearacyn())
				.acthProvyn(actranhBean.getProvyn())
				//.acthCrepno(actranhBean.getCrepno())
				.build();
	};
	
	public static Function<List<ActranhBean>, List<Actranh>> addActranhPojoEntityListMapping = actranhBeanList -> {
		return actranhBeanList.stream().map(actranhBean -> 
		 Actranh.builder()
				.actranhCK(ActranhCK.builder()
						.acthTranser(actranhBean.getTranser())
						.acthCoy(actranhBean.getCoy())
						.build())
				.acthTrantype(actranhBean.getTrantype())
				.acthTrandate(StringUtils.isNotBlank(actranhBean.getTrandate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(actranhBean.getTrandate()) : null)
				.acthVounum(actranhBean.getVounum())
				.acthProprietor(actranhBean.getProprietor())
				.acthCoytranser(actranhBean.getCoytranser())
				.acthLedgcode(actranhBean.getLedgcode())//hardcode in setter in service impl
				.acthPartytype(actranhBean.getPartytype())
				.acthPartycode(actranhBean.getPartycode())//concat 
				.acthTranamt(actranhBean.getTranamt())
				.acthVoudate(StringUtils.isNotBlank(actranhBean.getVoudate()) ? LocalDate.parse(actranhBean.getVoudate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.acthBankcode(actranhBean.getBankcode())
				.acthPostedyn(actranhBean.getPostedyn())
				.acthBalancedyn(actranhBean.getBalancedyn())
				.acthClosingjvyn(actranhBean.getClosingjvyn())
				.acthNarrative(actranhBean.getNarrative())
				.acthBbookyn(actranhBean.getBbookyn())
				.acthCbookyn(actranhBean.getCbookyn())
				.acthSite(actranhBean.getSite())
				.acthUserid(actranhBean.getUserid())
				.acthToday(LocalDateTime.now())
				.acthReverseyn(actranhBean.getReverseyn())
				.acthClearacyn(actranhBean.getClearacyn())
				.acthProvyn(actranhBean.getProvyn())
				//.acthCrepno(actranhBean.getCrepno())
				.build()).collect(Collectors.toList());
		
	};

	public static Function<List<ActrandBean>, List<Actrand>> addActrandPojoEntityMapping = actrandBeanList -> {
		return actrandBeanList.stream().map(actrandBean -> {
			return Actrand.builder()
					.actrandCK(ActrandCK.builder()
							.actdTranser(actrandBean.getTranser())	
							.actdBunum(actrandBean.getBunum())
							.actdCoy(actrandBean.getCoy())
							.build())
					.actdTrandate(StringUtils.isNotBlank(actrandBean.getTrandate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(actrandBean.getTrandate()) : null)
					.actdProprietor(actrandBean.getProprietor())
					.actdPartycode(actrandBean.getPartycode())
					.actdAcmajor(actrandBean.getAcmajor())
					.actdAcminor(actrandBean.getAcminor())
					.actdOldbunum(actrandBean.getOldbunum())
					.actdLedgcode(actrandBean.getLedgcode())
					.actdContrabunum(actrandBean.getContrabunum())
					.actdTrantype(actrandBean.getTrantype())
					.actdMintype(actrandBean.getMintype())
					.actdPartytype(actrandBean.getPartytype())
					.actdMincode(actrandBean.getMincode())
					.actdVounum(actrandBean.getVounum())
					.actdVoudate(StringUtils.isNotBlank(actrandBean.getVoudate()) ? LocalDate.parse(actrandBean.getVoudate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.actdTranamt(actrandBean.getTranamt())
					.actdPeriod(actrandBean.getPeriod())
					.actdReffrom(StringUtils.isNotBlank(actrandBean.getReffrom()) ? CommonUtils.INSTANCE.convertStringToDateFormat(actrandBean.getReffrom()) : null)
					.actdRefupto(StringUtils.isNotBlank(actrandBean.getRefupto()) ? CommonUtils.INSTANCE.convertStringToDateFormat(actrandBean.getRefupto()) : null)
					.actdNarrative(actrandBean.getNarrative())
					.actdProject(actrandBean.getProject())
					.actdProperty(actrandBean.getProperty())
					.actdBldgcode(actrandBean.getBldgcode())
					.actdWing(actrandBean.getWing())
					.actdFlat(actrandBean.getFlat())
					.actdDomain(actrandBean.getDomain())
					.actdContract(actrandBean.getContract())
					.actdWorkgroup(actrandBean.getWorkgroup())
					.actdWorkcode(actrandBean.getWorkcode())
					.actdMatgroup(actrandBean.getMatgroup())
					.actdMatcode(actrandBean.getMatcode())
					.actdSku(actrandBean.getSku())
					.actdItemqty(actrandBean.getItemqty())
					.actdCfgroup(actrandBean.getCfgroup())
					.actdCfcode(actrandBean.getCfcode())
					.actdDocnum(actrandBean.getDocnum())
					.actdDocdate(StringUtils.isNotBlank(actrandBean.getDocdate()) ? LocalDate.parse(actrandBean.getDocdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.actdPaymode(actrandBean.getPaymode())
					.actdDoctype(actrandBean.getDoctype())
					.actdDocpartype(actrandBean.getDocpartype())
					.actdDocparcode(actrandBean.getDocparcode())
					.actdXproject(actrandBean.getXproject())
					.actdXacmajor(actrandBean.getXacmajor())
					.actdXacminor(actrandBean.getXacminor())
					.actdXmintype(actrandBean.getXmintype())
					.actdXpartytype(actrandBean.getXpartytype())
					.actdXpartycode(actrandBean.getXpartycode())
					.actdXrefTranser(actrandBean.getXreftranser())
					.actdXrefBunum(actrandBean.getXrefbunum())
					.actdXrefTrandate(StringUtils.isNotBlank(actrandBean.getXreftrandate()) ? LocalDate.parse(actrandBean.getXreftrandate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.actdSite(actrandBean.getSite())
					.actdUserid(actrandBean.getUserid())
					.actdToday(LocalDateTime.now())
					.build();
		}).collect(Collectors.toList());
	};
	
	public static Function<OutchqRequestBean, Outchq> addOutchqPojoEntityMapping = outchqRequestBean -> {
		return Outchq.builder()
				.outchqCK(OutchqCK.builder()
						.outchqBank(outchqRequestBean.getBank())
						.outchqCoy(outchqRequestBean.getCoy())
						.outchqNum(outchqRequestBean.getNum())
						.outchqProprietor(outchqRequestBean.getProprietor())
						
						.build())
				.outchqAmount(outchqRequestBean.getAmount())
				.outchqSeries(outchqRequestBean.getSeries())
				.outchqTranser(outchqRequestBean.getTranser())
				.outchqBunum(outchqRequestBean.getBunum())
				.outchqCanceldate(StringUtils.isNotBlank(outchqRequestBean.getCanceldate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(outchqRequestBean.getCanceldate()) : null)
				.outchqOrigsite(outchqRequestBean.getOrigsite())
				.outchqPartycode(outchqRequestBean.getPartycode())
				.outchqPartytype(outchqRequestBean.getPartytype())
				.outchqPaydate(StringUtils.isNotBlank(outchqRequestBean.getPaydate()) ? LocalDate.parse(outchqRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.outchqPaymode(outchqRequestBean.getPaymode())
				.outchqPayref(outchqRequestBean.getPayref())
				.outchqPrinted(outchqRequestBean.getPrinted())
				.outchqPrintedon(StringUtils.isNotBlank(outchqRequestBean.getPrintedon()) ? CommonUtils.INSTANCE.convertStringToDateFormat(outchqRequestBean.getPrintedon()) : null)
				.outchqRecondate(StringUtils.isNotBlank(outchqRequestBean.getRecondate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(outchqRequestBean.getRecondate()) : null)
				.outchqReconstmt(outchqRequestBean.getReconstmt())
				.outchqSite(outchqRequestBean.getSite())
				.outchqToday(LocalDateTime.now())
				.outchqUserid(outchqRequestBean.getUserid())
				.build();
	};
}