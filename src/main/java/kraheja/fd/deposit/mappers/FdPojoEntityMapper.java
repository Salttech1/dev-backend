package kraheja.fd.deposit.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import kraheja.commons.bean.request.OutchqRequestBean;
import kraheja.commons.entity.Outchq;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.fd.deposit.bean.DepintBean;
import kraheja.fd.deposit.bean.request.DepositRequestBean;
import kraheja.fd.deposit.bean.request.DepositorRequestBean;
import kraheja.fd.deposit.bean.request.Form15hgRequestBean;
import kraheja.fd.deposit.entity.Depint;
import kraheja.fd.deposit.entity.DepintCK;
import kraheja.fd.deposit.entity.Deposit;
import kraheja.fd.deposit.entity.DepositCK;
import kraheja.fd.deposit.entity.Depositor;
import kraheja.fd.deposit.entity.DepositorCK;
import kraheja.fd.deposit.entity.Form15hg;
import kraheja.fd.deposit.entity.Form15hgCK;

public interface FdPojoEntityMapper {

	final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static Function<Object[], Deposit> addDepositFdPojoEntityMapping = objectArray -> {
		DepositRequestBean depositRequestBean = (DepositRequestBean) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String receiptnum = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);

		Deposit.DepositBuilder depositbuilder = Deposit.builder();
		depositbuilder 
		.depositCK(DepositCK.builder()
				.depDepositor(depositRequestBean.getDepositor())
				.depReceiptnum(receiptnum.trim())
				.depCoy(depositRequestBean.getCoy())
				.build())
		.depDepdate(StringUtils.isNotBlank(depositRequestBean.getDepdate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getDepdate()) : null)
		.depProprietor(depositRequestBean.getProprietor())
		.depStaffyn(depositRequestBean.getStaffyn())
		.depBankcode(depositRequestBean.getBankcode())
		.depDepamount(depositRequestBean.getDepamount())
		.depLiqtype(depositRequestBean.getLiqtype())
		.depDepmonths(depositRequestBean.getDepmonths())
		.depIntrate(depositRequestBean.getIntrate())
		.depIntfreq(depositRequestBean.getIntfreq())
		.depOrigrate(depositRequestBean.getOrigrate())
		.depBankcode(depositRequestBean.getBankcode())
		.depBrokerage(BigInteger.ZERO.doubleValue())
		.depBroktds(BigInteger.ZERO.doubleValue())
		.depBrokcheq(depositRequestBean.getBrokcheq())
		.depBrokper(depositRequestBean.getBrokper())
		.depBrokcheqdate(StringUtils.isNotBlank(depositRequestBean.getBrokcheqdate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getBrokcheqdate()) : null)
		.depTds(BigInteger.ZERO.doubleValue())
		.depDisdate(StringUtils.isNotBlank(depositRequestBean.getDisdate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getDisdate()) : null)
		.depMatdate(StringUtils.isNotBlank(depositRequestBean.getMatdate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getMatdate()) : null)
		.depCanceldate(StringUtils.isNotBlank(depositRequestBean.getCanceldate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getCanceldate()) : null)
		.depPrintedon(StringUtils.isNotBlank(depositRequestBean.getPrintedon()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getPrintedon()) : null)
		.depPrintrev(BigInteger.ZERO.doubleValue())
		.depMatamount(depositRequestBean.getMatamount())
		.depGrossint(depositRequestBean.getGrossint())
		.depAccint(BigInteger.ZERO.doubleValue())
		.depIntaccyymm(depositRequestBean.getIntaccyymm())
		.depIntpaidytd(BigInteger.ZERO.doubleValue())
		.depTaxpaidytd(BigInteger.ZERO.doubleValue())
		.depOrigreceipt(depositRequestBean.getOrigreceipt())
		.depPayref(depositRequestBean.getPayref())
		.depPaydate(StringUtils.isNotBlank(depositRequestBean.getPaydate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getPaydate()) : null)
		.depPayamount(depositRequestBean.getPayamount())
		.depJowner1(depositRequestBean.getJowner1())
		.depJowner2(depositRequestBean.getJowner2())
		.depJowner3(depositRequestBean.getJowner3())
		.depNgid1(depositRequestBean.getNgid1())
		.depNgid2(depositRequestBean.getNgid2())
		.depNgid3(depositRequestBean.getNgid3())
		.depNgname1(depositRequestBean.getNgname1())
		.depNgname2(depositRequestBean.getNgname2())
		.depNgname3(depositRequestBean.getNgname3())
		.depDeporigin(StringUtils.isNotBlank(depositRequestBean.getDeporigin()) ? depositRequestBean.getDeporigin().trim() : null)	
		.depPayeecode(depositRequestBean.getPayeecode())
		.depInstructions(depositRequestBean.getInstructions())
		.depTransfer(StringUtils.isNotBlank(depositRequestBean.getTransfer()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getTransfer()) : null)
		.depSite(site)
		.depOrigsite(site)
		.depUserid(depositRequestBean.getUserid())
		.depToday(LocalDateTime.now())
		.depBroker(StringUtils.isNotBlank(depositRequestBean.getBroker()) ? depositRequestBean.getBroker().trim() : null)
		.depInstFdr(depositRequestBean.getInstfdr())
		.depInstBp(depositRequestBean.getInstbp())
		.depInstIp(depositRequestBean.getInstip())
		.depWarBank(depositRequestBean.getWarbank())
		.depWarAcc(depositRequestBean.getWaracc())
		.depExpiryStatus(depositRequestBean.getExpirystatus())
		.depNominee(depositRequestBean.getNominee())
		.depExtendedyn(depositRequestBean.getExtendedyn())
		.depReceiptdate(StringUtils.isNotBlank(depositRequestBean.getReceiptdate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getReceiptdate()) : null)
		.depPrevmaturitydate(StringUtils.isNotBlank(depositRequestBean.getPrevmaturitydate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(depositRequestBean.getPrevmaturitydate()) : null)
		.depIntrate2(depositRequestBean.getIntrate2())
		.depIntrateold(depositRequestBean.getIntrateold());
		return depositbuilder.build();
	};

	public static Function<Object[], Depositor> addDepositorFdPojoEntityMapping = objectArray -> {
		DepositorRequestBean depositorRequestBean =(DepositorRequestBean) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String newDepositorId = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);

		Depositor.DepositorBuilder depositorbuilder = Depositor.builder();
		depositorbuilder.depositorCK(DepositorCK.builder()
				.deptrCoy(depositorRequestBean.getCompanyCode())
				.deptrDepositor(newDepositorId)
				.build())
		.deptrTitle(StringUtils.isNotBlank(depositorRequestBean.getTitle()) ? depositorRequestBean.getTitle().trim() : null)
		.deptrName(StringUtils.isNotBlank(depositorRequestBean.getName()) ? depositorRequestBean.getName().trim() : null)
		.deptrProprietor(StringUtils.isNotBlank(depositorRequestBean.getProprietor()) ? depositorRequestBean.getProprietor().trim() : null)
		.deptrDepamount(BigInteger.ZERO.doubleValue())
		.deptrGrossint(BigInteger.ZERO.doubleValue())
		.deptrAccint(BigInteger.ZERO.doubleValue())
		.deptrTds(BigInteger.ZERO.doubleValue())
		.deptrRemarks(StringUtils.isNotBlank(depositorRequestBean.getRemarks()) ? depositorRequestBean.getRemarks().trim() : null)
		.deptrCity(StringUtils.isNotBlank(depositorRequestBean.getCity()) ? depositorRequestBean.getCity().trim() : null)
		.deptrSite(site)
		.deptrUserid(depositorRequestBean.getUserid())
		.deptrToday(LocalDateTime.now())
		.deptrOrigsite(CommonConstraints.INSTANCE.SITE)
		.deptrDeptype(StringUtils.isNotBlank(depositorRequestBean.getDeptype()) ? depositorRequestBean.getDeptype().trim() : null)
		.deptrTaxalwaysyn(StringUtils.isNotBlank(depositorRequestBean.getTaxalwaysyn()) ? depositorRequestBean.getTaxalwaysyn().trim() : CommonConstraints.INSTANCE.LETTER_Y)
		.deptrTds15hyn(StringUtils.isNotBlank(depositorRequestBean.getTds15hyn()) ? depositorRequestBean.getTds15hyn().trim() : null)
		.deptrTds15gyn(StringUtils.isNotBlank(depositorRequestBean.getTds15gyn()) ? depositorRequestBean.getTds15gyn().trim() : null)
		.deptrIntpaidytd(BigInteger.ZERO.doubleValue())
		.deptrTaxpaidytd(BigInteger.ZERO.doubleValue())
		.deptrTaxrecytd(BigInteger.ZERO.doubleValue())
		.deptrClubref(StringUtils.isNotBlank(depositorRequestBean.getClubref()) ? depositorRequestBean.getClubref().trim() : null)
		.deptrPanum1(StringUtils.isNotBlank(depositorRequestBean.getPannum1()) ? depositorRequestBean.getPannum1().trim() : null)
		.deptrPanum2(StringUtils.isNotBlank(depositorRequestBean.getPannum2()) ? depositorRequestBean.getPannum2().trim() : null)
		.deptrBirthdate(Objects.nonNull(depositorRequestBean.getBirthdate()) ? CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(depositorRequestBean.getBirthdate()) : null)
		.deptr15hrecd(Objects.nonNull(depositorRequestBean.getFifteenhrecd()) ? CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(depositorRequestBean.getFifteenhrecd()) : null)
		.deptr15hsubd(Objects.nonNull(depositorRequestBean.getFifteenhsubd()) ? CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(depositorRequestBean.getFifteenhsubd()) : null);
		return depositorbuilder.build();
	};

	public static BiFunction<Depositor, DepositorRequestBean, Depositor> updateDepositorPojoEntityMapping = (depositorEntity, depositorRequestBean) -> {
		depositorEntity.getDepositorCK().setDeptrCoy(Objects.nonNull(depositorRequestBean.getCompanyCode()) ? depositorRequestBean.getCompanyCode(): depositorEntity.getDepositorCK().getDeptrCoy());
		depositorEntity.getDepositorCK().setDeptrDepositor(Objects.nonNull(depositorRequestBean.getDepositor()) ? depositorRequestBean.getDepositor() : depositorEntity.getDepositorCK().getDeptrDepositor());
		depositorEntity.setDeptrTitle(Objects.nonNull(depositorRequestBean.getTitle()) ? depositorRequestBean.getTitle().trim() : depositorEntity.getDeptrTitle());
		depositorEntity.setDeptrName(Objects.nonNull(depositorRequestBean.getName()) ? depositorRequestBean.getName().trim() : depositorEntity.getDeptrName());
		depositorEntity.setDeptrDepamount(Objects.nonNull(depositorRequestBean.getDepamount()) ? depositorRequestBean.getDepamount() : depositorEntity.getDeptrDepamount());
		depositorEntity.setDeptrGrossint(Objects.nonNull(depositorRequestBean.getGrossint()) ? depositorRequestBean.getGrossint() : depositorEntity.getDeptrGrossint());
		depositorEntity.setDeptrAccint(Objects.nonNull(depositorRequestBean.getAccint()) ? depositorRequestBean.getAccint() : depositorEntity.getDeptrAccint());
		depositorEntity.setDeptrTds(Objects.nonNull(depositorRequestBean.getTds()) ? depositorRequestBean.getTds() : depositorEntity.getDeptrTds());
		depositorEntity.setDeptrRemarks(Objects.nonNull(depositorRequestBean.getRemarks()) ? depositorRequestBean.getRemarks().trim() : depositorEntity.getDeptrRemarks());
		depositorEntity.setDeptrCity(Objects.nonNull(depositorRequestBean.getCity()) ? depositorRequestBean.getCity().trim() : depositorEntity.getDeptrCity());
		depositorEntity.setDeptrSite(Objects.nonNull(depositorRequestBean.getSite()) ? depositorRequestBean.getSite().trim() : depositorEntity.getDeptrSite());
		depositorEntity.setDeptrUserid(Objects.nonNull(depositorRequestBean.getUserid()) ? depositorRequestBean.getUserid().trim() : depositorEntity.getDeptrUserid());
		depositorEntity.setDeptrToday(Objects.nonNull(depositorRequestBean.getToday()) ?  LocalDateTime.now() : depositorEntity.getDeptrToday());
		depositorEntity.setDeptrOrigsite(Objects.nonNull(depositorRequestBean.getOrigsite()) ? depositorRequestBean.getOrigsite().trim() : depositorEntity.getDeptrOrigsite());
		depositorEntity.setDeptrDeptype(Objects.nonNull(depositorRequestBean.getDeptype()) ? depositorRequestBean.getDeptype().trim() : depositorEntity.getDeptrDeptype());
		depositorEntity.setDeptrTaxalwaysyn(Objects.nonNull(depositorRequestBean.getTaxalwaysyn()) ? depositorRequestBean.getTaxalwaysyn().trim() : depositorEntity.getDeptrTaxalwaysyn());
		depositorEntity.setDeptrTds15hyn(Objects.nonNull(depositorRequestBean.getTds15hyn()) ? depositorRequestBean.getTds15hyn().trim() : depositorEntity.getDeptrTds15hyn());
		depositorEntity.setDeptrTds15gyn(Objects.nonNull(depositorRequestBean.getTds15gyn()) ? depositorRequestBean.getTds15gyn().trim() : depositorEntity.getDeptrTds15gyn());
		depositorEntity.setDeptrIntpaidytd(Objects.nonNull(depositorRequestBean.getIntpayedytd()) ? depositorRequestBean.getIntpayedytd() : depositorEntity.getDeptrIntpaidytd());
		depositorEntity.setDeptrTaxpaidytd(Objects.nonNull(depositorRequestBean.getTaxpaidytd()) ? depositorRequestBean.getTaxpaidytd() : depositorEntity.getDeptrTaxpaidytd());
		depositorEntity.setDeptrTaxrecytd(Objects.nonNull(depositorRequestBean.getTaxrecytd()) ? depositorRequestBean.getTaxrecytd() : depositorEntity.getDeptrTaxrecytd());
		depositorEntity.setDeptrClubref(Objects.nonNull(depositorRequestBean.getClubref()) ? depositorRequestBean.getClubref().trim() : depositorEntity.getDeptrClubref());
		depositorEntity.setDeptrPanum1(Objects.nonNull(depositorRequestBean.getPannum1()) ? depositorRequestBean.getPannum1().trim() : depositorEntity.getDeptrPanum1());
		depositorEntity.setDeptrPanum2(Objects.nonNull(depositorRequestBean.getPannum2()) ? depositorRequestBean.getPannum2().trim() : depositorEntity.getDeptrPanum2());
		depositorEntity.setDeptrBirthdate(Objects.nonNull(depositorRequestBean.getBirthdate()) ? CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(depositorRequestBean.getBirthdate()) : depositorEntity.getDeptrBirthdate());
		depositorEntity.setDeptr15hrecd(Objects.nonNull(depositorRequestBean.getFifteenhrecd()) ? CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(depositorRequestBean.getFifteenhrecd()) : depositorEntity.getDeptr15hrecd());
		depositorEntity.setDeptr15hsubd(Objects.nonNull(depositorRequestBean.getFifteenhsubd()) ? CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(depositorRequestBean.getFifteenhsubd()) : depositorEntity.getDeptr15hrecd());
		return depositorEntity;
	};

	public static Function<Object[], Depint> addDepintEntityPojoMapper = objectArray -> {
		DepintBean depintRequestBean = (DepintBean) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);

		Depint.DepintBuilder depintbuilder = Depint.builder();
		depintbuilder.depintCK(DepintCK.builder()
				.dinDepositor(depintRequestBean.getDepositor())
				.dinReceiptnum(depintRequestBean.getReceiptnum())
				.dinIntfrom(StringUtils.isNotBlank(depintRequestBean.getIntfrom()) ? LocalDate.parse(depintRequestBean.getIntfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.dinIntupto(StringUtils.isNotBlank(depintRequestBean.getIntupto()) ? LocalDate.parse(depintRequestBean.getIntupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.dinCoy(depintRequestBean.getCoy())
				.dinChqnum(depintRequestBean.getChqnum())
				.build())
		.dinBankcode(depintRequestBean.getBankcode())
		.dinFromdate(StringUtils.isNotBlank(depintRequestBean.getFromdate()) ? LocalDate.parse(depintRequestBean.getFromdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.dinTodate(StringUtils.isNotBlank(depintRequestBean.getTodate()) ? LocalDate.parse(depintRequestBean.getTodate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.dinCanceldate(StringUtils.isNotBlank(depintRequestBean.getCanceldate()) ? LocalDate.parse(depintRequestBean.getCanceldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.dinInterest(depintRequestBean.getInterest())
		.dinNewprin(depintRequestBean.getNewprin())
		.dinOrigsite(depintRequestBean.getOrigsite())
		.dinProprietor(depintRequestBean.getProprietor())
		.dinSite(site)
		.dinStaffallow(depintRequestBean.getStaffallow())
		.dinTaxalwaysyn(depintRequestBean.getTaxalwaysyn())
		.dinTds(depintRequestBean.getTds())
		.dinTds15hyn(depintRequestBean.getTds15hyn())
		.dinToday(LocalDateTime.now())
		.dinTranser(depintRequestBean.getTranser())
		.dinUserid(depintRequestBean.getUserid())
		;

		return depintbuilder.build();
	};

	public static BiFunction<Depint, DepintBean, Depint> updateDepintEntityPojoMapper = (depintEntity, depintRequestBean) -> {
		depintEntity.getDepintCK().setDinDepositor(StringUtils.isNotBlank(depintRequestBean.getDepositor()) ? depintRequestBean.getDepositor().trim() : depintEntity.getDepintCK().getDinDepositor());
		depintEntity.getDepintCK().setDinReceiptnum(StringUtils.isNotBlank(depintRequestBean.getReceiptnum()) ? depintRequestBean.getReceiptnum().trim() : depintEntity.getDepintCK().getDinReceiptnum());
		depintEntity.getDepintCK().setDinIntfrom(StringUtils.isNotBlank(depintRequestBean.getIntfrom()) ? LocalDateTime.parse(depintRequestBean.getIntfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toLocalDate() : depintEntity.getDepintCK().getDinIntfrom());
		depintEntity.getDepintCK().setDinIntupto(StringUtils.isNotBlank(depintRequestBean.getIntupto()) ? LocalDateTime.parse(depintRequestBean.getIntupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toLocalDate() : depintEntity.getDepintCK().getDinIntupto());
		depintEntity.getDepintCK().setDinCoy(StringUtils.isNotBlank(depintRequestBean.getCoy()) ? depintRequestBean.getCoy().trim() : depintEntity.getDepintCK().getDinCoy());
		depintEntity.getDepintCK().setDinChqnum(StringUtils.isNotBlank(depintRequestBean.getChqnum()) ? depintRequestBean.getChqnum().trim() : depintEntity.getDepintCK().getDinChqnum());
		depintEntity.setDinBankcode(StringUtils.isNotBlank(depintRequestBean.getBankcode()) ? depintRequestBean.getBankcode().trim() : depintEntity.getDinBankcode());
		depintEntity.setDinCanceldate(Objects.nonNull(depintRequestBean.getCanceldate()) ?  LocalDateTime.parse( depintRequestBean.getCanceldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toLocalDate() : depintEntity.getDinCanceldate());
		depintEntity.setDinInterest(Objects.nonNull(depintRequestBean.getInterest()) ? depintRequestBean.getInterest() : depintEntity.getDinInterest());
		depintEntity.setDinNewprin(Objects.nonNull(depintRequestBean.getNewprin()) ? depintRequestBean.getNewprin() : depintEntity.getDinNewprin());
		depintEntity.setDinOrigsite(StringUtils.isNotBlank(depintRequestBean.getOrigsite()) ? depintRequestBean.getOrigsite().trim() : depintEntity.getDinOrigsite());
		depintEntity.setDinProprietor(StringUtils.isNotBlank(depintRequestBean.getProprietor()) ? depintRequestBean.getProprietor().trim() : depintEntity.getDinProprietor());
		depintEntity.setDinSite(StringUtils.isNotBlank(depintRequestBean.getSite()) ? depintRequestBean.getSite().trim() : depintEntity.getDinSite());
		depintEntity.setDinStaffallow(Objects.nonNull(depintRequestBean.getStaffallow()) ? depintRequestBean.getStaffallow() : depintEntity.getDinStaffallow());
		depintEntity.setDinTaxalwaysyn(StringUtils.isNotBlank(depintRequestBean.getTaxalwaysyn()) ? depintRequestBean.getTaxalwaysyn().trim() : depintEntity.getDinTaxalwaysyn());
		depintEntity.setDinTds(Objects.nonNull(depintRequestBean.getTds()) ? depintRequestBean.getTds() : depintEntity.getDinTds());
		depintEntity.setDinTds15hyn(StringUtils.isNotBlank(depintRequestBean.getTds15hyn()) ? depintRequestBean.getTds15hyn().trim() : depintEntity.getDinTds15hyn());
		depintEntity.setDinToday(StringUtils.isNotBlank(depintRequestBean.getToday()) ? LocalDateTime.now() : depintEntity.getDinToday());
		depintEntity.setDinTranser(StringUtils.isNotBlank(depintRequestBean.getTranser()) ? depintRequestBean.getTranser().trim() : depintEntity.getDinTranser());
		depintEntity.setDinUserid(StringUtils.isNotBlank(depintRequestBean.getUserid()) ? depintRequestBean.getUserid().trim() : depintEntity.getDinUserid());
		depintEntity.setDinFromdate(StringUtils.isNotBlank(depintRequestBean.getFromdate()) ? LocalDateTime.parse(depintRequestBean.getFromdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toLocalDate() : depintEntity.getDinFromdate());
		depintEntity.setDinTodate(StringUtils.isNotBlank(depintRequestBean.getTodate()) ? LocalDateTime.parse(depintRequestBean.getTodate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toLocalDate() : depintEntity.getDinTodate());
		return depintEntity;
	};

	public static BiFunction<Deposit, DepositRequestBean, Deposit> updateDepositPojoEntityMapper = (depositEntity, depositRequestBean) -> {
		depositEntity.getDepositCK().setDepCoy(StringUtils.isNotBlank(depositRequestBean.getCoy()) ? depositRequestBean.getCoy().trim() : depositEntity.getDepositCK().getDepCoy());
		depositEntity.getDepositCK().setDepDepositor(StringUtils.isNotBlank(depositRequestBean.getDepositor()) ? depositRequestBean.getDepositor().trim() : depositEntity.getDepositCK().getDepDepositor());
		depositEntity.getDepositCK().setDepReceiptnum(StringUtils.isNotBlank(depositRequestBean.getReceiptnum()) ? depositRequestBean.getReceiptnum().trim() : depositEntity.getDepositCK().getDepReceiptnum());
		depositEntity.setDepAccint(Objects.nonNull(depositRequestBean.getAccint()) ? depositRequestBean.getAccint() : depositEntity.getDepAccint());
		depositEntity.setDepBankcode(StringUtils.isNotBlank(depositRequestBean.getBankcode()) ? depositRequestBean.getBankcode().trim() : depositEntity.getDepBankcode());
		depositEntity.setDepBrokcheq(StringUtils.isNotBlank(depositRequestBean.getBrokcheq()) ? depositRequestBean.getBrokcheq().trim() : depositEntity.getDepBrokcheq());
		depositEntity.setDepBrokcheqdate(Objects.nonNull(depositRequestBean.getBrokcheqdate()) ? LocalDateTime.parse(depositRequestBean.getBrokcheqdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depositEntity.getDepBrokcheqdate());
		depositEntity.setDepBroker(StringUtils.isNotBlank(depositRequestBean.getBroker()) ? depositRequestBean.getBroker().trim() : depositEntity.getDepBroker());
		depositEntity.setDepBrokerage(Objects.nonNull(depositRequestBean.getBrokerage()) ? depositRequestBean.getBrokerage() : depositEntity.getDepBrokerage());
		depositEntity.setDepBrokper(Objects.nonNull(depositRequestBean.getBrokper()) ? depositRequestBean.getBrokper() : depositEntity.getDepBrokper());
		depositEntity.setDepBroktds(Objects.nonNull(depositRequestBean.getBroktds()) ? depositRequestBean.getBroktds() : depositEntity.getDepBroktds());
		depositEntity.setDepCanceldate(Objects.nonNull(depositRequestBean.getCanceldate()) ? LocalDateTime.parse(depositRequestBean.getCanceldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depositEntity.getDepCanceldate());
		depositEntity.setDepDepamount(Objects.nonNull(depositRequestBean.getDepamount()) ? depositRequestBean.getDepamount() : depositEntity.getDepDepamount());
		depositEntity.setDepDepdate(Objects.nonNull(depositRequestBean.getDepdate()) ? LocalDateTime.parse(depositRequestBean.getDepdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depositEntity.getDepDepdate());
		depositEntity.setDepDepmonths(Objects.nonNull(depositRequestBean.getDepmonths()) ? depositRequestBean.getDepmonths() : depositEntity.getDepDepmonths());
		depositEntity.setDepDeporigin(StringUtils.isNotBlank(depositRequestBean.getDeporigin()) ? depositRequestBean.getDeporigin().trim() : depositEntity.getDepDeporigin());
		depositEntity.setDepDisdate(Objects.nonNull(depositRequestBean.getDisdate()) ? LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : depositEntity.getDepDisdate());
		depositEntity.setDepExpiryStatus(StringUtils.isNotBlank(depositRequestBean.getExpirystatus()) ? depositRequestBean.getExpirystatus().trim() : depositEntity.getDepExpiryStatus());
		depositEntity.setDepExtendedyn(StringUtils.isNotBlank(depositRequestBean.getExtendedyn()) ? depositRequestBean.getExtendedyn().trim() : depositEntity.getDepExtendedyn());
		depositEntity.setDepGrossint(Objects.nonNull(depositRequestBean.getGrossint()) ? depositRequestBean.getGrossint() : depositEntity.getDepGrossint());
		depositEntity.setDepInstructions(StringUtils.isNotBlank(depositRequestBean.getInstructions()) ? depositRequestBean.getInstructions().trim() : depositEntity.getDepInstructions());
		depositEntity.setDepInstBp(StringUtils.isNotBlank(depositRequestBean.getInstbp()) ? depositRequestBean.getInstbp().trim() : depositEntity.getDepInstBp());
		depositEntity.setDepInstFdr(StringUtils.isNotBlank(depositRequestBean.getInstfdr()) ? depositRequestBean.getInstfdr().trim() : depositEntity.getDepInstFdr());
		depositEntity.setDepInstIp(StringUtils.isNotBlank(depositRequestBean.getInstip()) ? depositRequestBean.getInstip().trim() : depositEntity.getDepInstIp());
		depositEntity.setDepIntaccyymm(StringUtils.isNotBlank(depositRequestBean.getIntaccyymm()) ? depositRequestBean.getIntaccyymm().trim() : depositEntity.getDepIntaccyymm());
		depositEntity.setDepIntfreq(Objects.nonNull(depositRequestBean.getIntfreq()) ? depositRequestBean.getIntfreq() : depositEntity.getDepIntfreq());
		depositEntity.setDepIntpaidytd(Objects.nonNull(depositRequestBean.getIntpaidytd()) ? depositRequestBean.getIntpaidytd() : depositEntity.getDepIntpaidytd());
		depositEntity.setDepIntrate(Objects.nonNull(depositRequestBean.getIntrate()) ? depositRequestBean.getIntrate() : depositEntity.getDepIntrate());
		depositEntity.setDepIntrate2(Objects.nonNull(depositRequestBean.getIntrate2()) ? depositRequestBean.getIntrate2() : depositEntity.getDepIntrate2());
		depositEntity.setDepIntrateold(Objects.nonNull(depositRequestBean.getIntrateold()) ? depositRequestBean.getIntrateold() : depositEntity.getDepIntrateold());
		depositEntity.setDepJowner1(StringUtils.isNotBlank(depositRequestBean.getJowner1()) ? depositRequestBean.getJowner1().trim() : depositEntity.getDepJowner1());
		depositEntity.setDepJowner2(StringUtils.isNotBlank(depositRequestBean.getJowner2()) ? depositRequestBean.getJowner2().trim() : depositEntity.getDepJowner2());
		depositEntity.setDepJowner3(StringUtils.isNotBlank(depositRequestBean.getJowner3()) ? depositRequestBean.getJowner3().trim() : depositEntity.getDepJowner3());
		depositEntity.setDepLiqtype(StringUtils.isNotBlank(depositRequestBean.getLiqtype()) ? depositRequestBean.getLiqtype().trim() : depositEntity.getDepLiqtype());
		depositEntity.setDepMatamount(Objects.nonNull(depositRequestBean.getMatamount()) ? depositRequestBean.getMatamount() : depositEntity.getDepMatamount());
		depositEntity.setDepMatdate(Objects.nonNull(depositRequestBean.getMatdate()) ? LocalDateTime.parse(depositRequestBean.getMatdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depositEntity.getDepMatdate());
		depositEntity.setDepNgid1(StringUtils.isNotBlank(depositRequestBean.getNgid1()) ? depositRequestBean.getNgid1().trim() : depositEntity.getDepNgid1());
		depositEntity.setDepNgid2(StringUtils.isNotBlank(depositRequestBean.getNgid2()) ? depositRequestBean.getNgid2().trim() : depositEntity.getDepNgid2());
		depositEntity.setDepNgid3(StringUtils.isNotBlank(depositRequestBean.getNgid3()) ? depositRequestBean.getNgid3().trim() : depositEntity.getDepNgid3());
		depositEntity.setDepNgname1(StringUtils.isNotBlank(depositRequestBean.getNgname1()) ? depositRequestBean.getNgname1().trim() : depositEntity.getDepNgname1());
		depositEntity.setDepNgname2(StringUtils.isNotBlank(depositRequestBean.getNgname2()) ? depositRequestBean.getNgname2().trim() : depositEntity.getDepNgname2());
		depositEntity.setDepNgname3(StringUtils.isNotBlank(depositRequestBean.getNgname3()) ? depositRequestBean.getNgname3().trim() : depositEntity.getDepNgname3());
		depositEntity.setDepNominee(StringUtils.isNotBlank(depositRequestBean.getNominee()) ? depositRequestBean.getNominee().trim() : depositEntity.getDepNominee());
		depositEntity.setDepOrigrate(Objects.nonNull(depositRequestBean.getOrigrate()) ? depositRequestBean.getOrigrate() : depositEntity.getDepOrigrate());
		depositEntity.setDepOrigreceipt(StringUtils.isNotBlank(depositRequestBean.getOrigreceipt()) ? depositRequestBean.getOrigreceipt().trim() : depositEntity.getDepOrigreceipt());
		depositEntity.setDepOrigsite(StringUtils.isNotBlank(depositRequestBean.getOrigsite()) ? depositRequestBean.getOrigsite().trim() : depositEntity.getDepOrigsite());
		depositEntity.setDepPayamount(Objects.nonNull(depositRequestBean.getPayamount()) ? depositRequestBean.getPayamount() : depositEntity.getDepPayamount());
		depositEntity.setDepPaydate(Objects.nonNull(depositRequestBean.getPaydate()) ? LocalDateTime.parse(depositRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depositEntity.getDepPaydate());
		depositEntity.setDepPayeecode(StringUtils.isNotBlank(depositRequestBean.getPayeecode()) ? depositRequestBean.getPayeecode().trim() : depositEntity.getDepPayeecode());
		depositEntity.setDepPayref(StringUtils.isNotBlank(depositRequestBean.getPayref()) ? depositRequestBean.getPayref().trim() : depositEntity.getDepPayref());
		depositEntity.setDepPrevmaturitydate(Objects.nonNull(depositRequestBean.getPrevmaturitydate()) ? LocalDateTime.parse(depositRequestBean.getPrevmaturitydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depositEntity.getDepPrevmaturitydate());
		depositEntity.setDepPrintedon(Objects.nonNull(depositRequestBean.getPrintedon()) ? LocalDateTime.parse(depositRequestBean.getPrintedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depositEntity.getDepPrintedon());
		depositEntity.setDepPrintrev(Objects.nonNull(depositRequestBean.getPrintrev()) ? depositRequestBean.getPrintrev() : depositEntity.getDepPrintrev());
		depositEntity.setDepProprietor(StringUtils.isNotBlank(depositRequestBean.getProprietor()) ? depositRequestBean.getProprietor().trim() : depositEntity.getDepProprietor());
		depositEntity.setDepReceiptdate(Objects.nonNull(depositRequestBean.getReceiptdate()) ? LocalDateTime.parse(depositRequestBean.getReceiptdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depositEntity.getDepReceiptdate());
		depositEntity.setDepSite(StringUtils.isNotBlank(depositRequestBean.getSite()) ? depositRequestBean.getSite().trim() : depositEntity.getDepSite());
		depositEntity.setDepStaffyn(StringUtils.isNotBlank(depositRequestBean.getStaffyn()) ? depositRequestBean.getStaffyn().trim() : depositEntity.getDepStaffyn());
		depositEntity.setDepTaxpaidytd(Objects.nonNull(depositRequestBean.getTaxpaidytd()) ? depositRequestBean.getTaxpaidytd() : depositEntity.getDepTaxpaidytd());
		depositEntity.setDepTds(Objects.nonNull(depositRequestBean.getTds()) ? depositRequestBean.getTds() : depositEntity.getDepTds());
		depositEntity.setDepToday(Objects.nonNull(depositRequestBean.getToday()) ? LocalDateTime.now() : depositEntity.getDepToday());
		depositEntity.setDepTransfer(Objects.nonNull(depositRequestBean.getTransfer()) ? LocalDate.parse(depositRequestBean.getTransfer(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : depositEntity.getDepTransfer());
		depositEntity.setDepUserid(StringUtils.isNotBlank(depositRequestBean.getUserid()) ? depositRequestBean.getUserid().trim() : depositEntity.getDepUserid());
		depositEntity.setDepWarAcc(StringUtils.isNotBlank(depositRequestBean.getWaracc()) ? depositRequestBean.getWaracc().trim() : depositEntity.getDepWarAcc());
		depositEntity.setDepWarBank(StringUtils.isNotBlank(depositRequestBean.getWarbank()) ? depositRequestBean.getWarbank().trim() : depositEntity.getDepWarBank());
		return depositEntity;
	};

	public static BiFunction<List<Depint>, DepintBean, List<Depint>> updateDepintListEntityPojoMapper = (depintEntityList, depintRequestBean) -> {
		return depintEntityList.stream()
								.filter(depintEntity -> depintEntity.getDepintCK().getDinDepositor().equals(depintRequestBean.getDepositor().trim())
								&& depintEntity.getDepintCK().getDinCoy().equals(depintRequestBean.getCoy().trim()) && 
								depintEntity.getDepintCK().getDinReceiptnum().equals(depintRequestBean.getReceiptnum().trim())) 
				//				depintEntity.getDepintCK().getDinIntfrom().equals(depintRequestBean.getIntfrom()) &&
				//				depintEntity.getDepintCK().getDinIntupto().equals(depintRequestBean.getIntupto()))
				.map(depintEntity -> {
//					if(depintEntity.getDepintCK().getDinDepositor().equals(depintRequestBean.getDepositor().trim()) && 
//							depintEntity.getDepintCK().getDinCoy().equals(depintRequestBean.getCoy().trim()) && 
//							depintEntity.getDepintCK().getDinReceiptnum().equals(depintRequestBean.getReceiptnum().trim())) {
						depintEntity.getDepintCK().setDinDepositor(StringUtils.isNotBlank(depintRequestBean.getDepositor()) ? depintRequestBean.getDepositor().trim() : depintEntity.getDepintCK().getDinDepositor());
						depintEntity.getDepintCK().setDinReceiptnum(StringUtils.isNotBlank(depintRequestBean.getReceiptnum()) ? depintRequestBean.getReceiptnum().trim() : depintEntity.getDepintCK().getDinReceiptnum());
						depintEntity.getDepintCK().setDinIntfrom(StringUtils.isNotBlank(depintRequestBean.getIntfrom()) ? LocalDate.parse(depintRequestBean.getIntfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depintEntity.getDepintCK().getDinIntfrom());
						depintEntity.getDepintCK().setDinIntupto(StringUtils.isNotBlank(depintRequestBean.getIntupto()) ? LocalDate.parse(depintRequestBean.getIntupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depintEntity.getDepintCK().getDinIntupto());
						depintEntity.getDepintCK().setDinCoy(StringUtils.isNotBlank(depintRequestBean.getCoy()) ? depintRequestBean.getCoy().trim() : depintEntity.getDepintCK().getDinCoy());
						depintEntity.getDepintCK().setDinChqnum(StringUtils.isNotBlank(depintRequestBean.getChqnum()) ? depintRequestBean.getChqnum().trim() : depintEntity.getDepintCK().getDinChqnum());
						depintEntity.setDinBankcode(StringUtils.isNotBlank(depintRequestBean.getBankcode()) ? depintRequestBean.getBankcode().trim() : depintEntity.getDinBankcode());
						depintEntity.setDinCanceldate(Objects.nonNull(depintRequestBean.getCanceldate()) ?  LocalDate.parse(depintRequestBean.getCanceldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : depintEntity.getDinCanceldate());
						depintEntity.setDinInterest(Objects.nonNull(depintRequestBean.getInterest()) ? depintRequestBean.getInterest() : depintEntity.getDinInterest());
						depintEntity.setDinNewprin(Objects.nonNull(depintRequestBean.getNewprin()) ? depintRequestBean.getNewprin() : depintEntity.getDinNewprin());
						depintEntity.setDinOrigsite(StringUtils.isNotBlank(depintRequestBean.getOrigsite()) ? depintRequestBean.getOrigsite().trim() : depintEntity.getDinOrigsite());
						depintEntity.setDinProprietor(StringUtils.isNotBlank(depintRequestBean.getProprietor()) ? depintRequestBean.getProprietor().trim() : depintEntity.getDinProprietor());
						depintEntity.setDinSite(StringUtils.isNotBlank(depintRequestBean.getSite()) ? depintRequestBean.getSite().trim() : depintEntity.getDinSite());
						depintEntity.setDinStaffallow(Objects.nonNull(depintRequestBean.getStaffallow()) ? depintRequestBean.getStaffallow() : depintEntity.getDinStaffallow());
						depintEntity.setDinTaxalwaysyn(StringUtils.isNotBlank(depintRequestBean.getTaxalwaysyn()) ? depintRequestBean.getTaxalwaysyn().trim() : depintEntity.getDinTaxalwaysyn());
						depintEntity.setDinTds(Objects.nonNull(depintRequestBean.getTds()) ? depintRequestBean.getTds() : depintEntity.getDinTds());
						depintEntity.setDinTds15hyn(StringUtils.isNotBlank(depintRequestBean.getTds15hyn()) ? depintRequestBean.getTds15hyn().trim() : depintEntity.getDinTds15hyn());
						depintEntity.setDinToday(StringUtils.isNotBlank(depintRequestBean.getToday()) ? LocalDateTime.now() : depintEntity.getDinToday());
						depintEntity.setDinTranser(StringUtils.isNotBlank(depintRequestBean.getTranser()) ? depintRequestBean.getTranser().trim() : depintEntity.getDinTranser());
						depintEntity.setDinUserid(StringUtils.isNotBlank(depintRequestBean.getUserid()) ? depintRequestBean.getUserid().trim() : depintEntity.getDinUserid()); 
					return depintEntity;
				}).collect(Collectors.toList());
	};
	
	public static Function<Object[], Form15hg> addForm15hgEntityPojoMapper = objectArray -> {
		Form15hgRequestBean form15hgRequestBean = (Form15hgRequestBean) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);

		Form15hg.Form15hgBuilder form15hgbuilder = Form15hg.builder();
		form15hgbuilder.form15hgCK(Form15hgCK.builder()
				.formDepositor(form15hgRequestBean.getDepositor())
				.formCoy(form15hgRequestBean.getCoy())
				.formQuarter(form15hgRequestBean.getQuarter())
				.formAcyear(form15hgRequestBean.getAcyear())
				.build())
				.formAmtincomepaid(form15hgRequestBean.getAmtincomepaid())
				.formAssessedyn(form15hgRequestBean.getAssessedyn())
				.formAssessyear(form15hgRequestBean.getAssessyear())
				.formDatedeclreceived(StringUtils.isNotBlank(form15hgRequestBean.getDatedeclReceived()) ? LocalDate.parse(form15hgRequestBean.getDatedeclReceived(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : null)
				.formDateincomepaid(StringUtils.isNotBlank(form15hgRequestBean.getDateIncomePaid()) ? LocalDate.parse(form15hgRequestBean.getDateIncomePaid(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : null)
				.formEstimatedincome(form15hgRequestBean.getEstimatedIncome())
				.formEstimatedtotincome(form15hgRequestBean.getEstimatedtotIncome())
				.formIncomeformfiled(form15hgRequestBean.getIncomeFormFiled())
				.formOrigsite(site)
				.formSite(site)
				.formToday(LocalDateTime.now())
				.formTotalformfiled(form15hgRequestBean.getTotalFormFiled())
				.formUniqueid(form15hgRequestBean.getUniqueid())
				.formUserid(form15hgRequestBean.getUserid())
		;
		return form15hgbuilder.build();
	};
	
	public static BiFunction<Form15hg, Form15hgRequestBean, Form15hg> updateForm15hgPojoEntityMapper = (form15hgEntity, form15hgRequestBean) -> {
		form15hgEntity.getForm15hgCK().setFormAcyear(StringUtils.isNotBlank(form15hgRequestBean.getAcyear()) ? form15hgRequestBean.getAcyear().trim() : form15hgEntity.getForm15hgCK().getFormAcyear());
		form15hgEntity.getForm15hgCK().setFormCoy(StringUtils.isNotBlank(form15hgRequestBean.getCoy()) ? form15hgRequestBean.getCoy().trim() : form15hgEntity.getForm15hgCK().getFormCoy());
		form15hgEntity.getForm15hgCK().setFormDepositor(StringUtils.isNotBlank(form15hgRequestBean.getDepositor()) ? form15hgRequestBean.getDepositor().trim() : form15hgEntity.getForm15hgCK().getFormDepositor());
		form15hgEntity.getForm15hgCK().setFormQuarter(StringUtils.isNotBlank(form15hgRequestBean.getQuarter()) ? form15hgRequestBean.getQuarter().trim() : form15hgEntity.getForm15hgCK().getFormQuarter());
		form15hgEntity.setFormAmtincomepaid(Objects.nonNull(form15hgRequestBean.getAmtincomepaid()) ? form15hgRequestBean.getAmtincomepaid() : form15hgEntity.getFormAmtincomepaid());
		form15hgEntity.setFormAssessedyn(StringUtils.isNotBlank(form15hgRequestBean.getAssessedyn()) ? form15hgRequestBean.getAssessedyn().trim() : form15hgEntity.getFormAssessedyn());
		form15hgEntity.setFormAssessyear(StringUtils.isNotBlank(form15hgRequestBean.getAssessyear()) ? form15hgRequestBean.getAssessyear().trim() : form15hgEntity.getFormAssessyear());
		form15hgEntity.setFormDatedeclreceived(StringUtils.isNotBlank(form15hgRequestBean.getDatedeclReceived()) ? LocalDate.parse(form15hgRequestBean.getDatedeclReceived(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : form15hgEntity.getFormDatedeclreceived());
		form15hgEntity.setFormIncomeformfiled(Objects.nonNull(form15hgRequestBean.getIncomeFormFiled()) ? form15hgRequestBean.getIncomeFormFiled() : form15hgEntity.getFormIncomeformfiled());
		form15hgEntity.setFormDateincomepaid(StringUtils.isNotBlank(form15hgRequestBean.getDateIncomePaid()) ? LocalDate.parse(form15hgRequestBean.getDateIncomePaid(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : form15hgEntity.getFormDateincomepaid());
		form15hgEntity.setFormEstimatedincome(Objects.nonNull(form15hgRequestBean.getEstimatedIncome()) ? form15hgRequestBean.getEstimatedIncome() : form15hgEntity.getFormEstimatedincome());
		form15hgEntity.setFormEstimatedtotincome(Objects.nonNull(form15hgRequestBean.getEstimatedtotIncome()) ? form15hgRequestBean.getEstimatedtotIncome() : form15hgEntity.getFormEstimatedtotincome());
		form15hgEntity.setFormOrigsite(StringUtils.isNotBlank(form15hgRequestBean.getOrigsite()) ? form15hgRequestBean.getOrigsite().trim() : form15hgEntity.getFormOrigsite());
		form15hgEntity.setFormSite(StringUtils.isNotBlank(form15hgRequestBean.getSite()) ? form15hgRequestBean.getSite().trim() : form15hgEntity.getFormSite());
		form15hgEntity.setFormToday(LocalDateTime.now());
		form15hgEntity.setFormTotalformfiled(Objects.nonNull(form15hgRequestBean.getTotalFormFiled()) ? form15hgRequestBean.getTotalFormFiled() : form15hgEntity.getFormTotalformfiled());
		form15hgEntity.setFormUniqueid(StringUtils.isNotBlank(form15hgRequestBean.getUniqueid()) ? form15hgRequestBean.getUniqueid().trim() : form15hgEntity.getFormUniqueid());
		form15hgEntity.setFormUserid(StringUtils.isNotBlank(form15hgRequestBean.getUserid()) ? form15hgRequestBean.getUserid().trim() : form15hgEntity.getFormUserid());
		return form15hgEntity;
	};
	
	public static BiFunction<Outchq, OutchqRequestBean, Outchq> updateOutchqEntityPojoMapper = (outchqEntity, outchqRequestBean) -> {
		outchqEntity.getOutchqCK().setOutchqProprietor(StringUtils.isNotBlank(outchqRequestBean.getProprietor()) ? outchqRequestBean.getProprietor().trim() : outchqEntity.getOutchqCK().getOutchqProprietor());
		outchqEntity.getOutchqCK().setOutchqCoy(StringUtils.isNotBlank(outchqRequestBean.getCoy()) ? outchqRequestBean.getCoy().trim() : outchqEntity.getOutchqCK().getOutchqCoy());
		outchqEntity.getOutchqCK().setOutchqBank(StringUtils.isNotBlank(outchqRequestBean.getBank()) ? outchqRequestBean.getBank().trim() : outchqEntity.getOutchqCK().getOutchqBank());
		outchqEntity.setOutchqSeries(StringUtils.isNotBlank(outchqRequestBean.getSeries()) ? outchqRequestBean.getSeries().trim() : outchqEntity.getOutchqSeries());
		outchqEntity.getOutchqCK().setOutchqNum(StringUtils.isNotBlank(outchqRequestBean.getNum()) ? outchqRequestBean.getNum().trim() : outchqEntity.getOutchqCK().getOutchqNum());
		outchqEntity.setOutchqTranser(StringUtils.isNotBlank(outchqRequestBean.getTranser()) ? outchqRequestBean.getTranser().trim() : outchqEntity.getOutchqTranser());
		outchqEntity.setOutchqAmount(Objects.nonNull(outchqRequestBean.getAmount()) ? outchqRequestBean.getAmount() : outchqEntity.getOutchqAmount());
		outchqEntity.setOutchqBunum(Objects.nonNull(outchqRequestBean.getBunum()) ? outchqRequestBean.getBunum() : outchqEntity.getOutchqBunum());
		outchqEntity.setOutchqCanceldate(Objects.nonNull(outchqRequestBean.getCanceldate()) ? LocalDate.parse(outchqRequestBean.getCanceldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : outchqEntity.getOutchqCanceldate());
		outchqEntity.setOutchqOrigsite(StringUtils.isNotBlank(outchqRequestBean.getOrigsite()) ? outchqRequestBean.getOrigsite().trim() : outchqEntity.getOutchqOrigsite());
		outchqEntity.setOutchqOrigsys(StringUtils.isNotBlank(outchqRequestBean.getOrigsys()) ? outchqRequestBean.getOrigsys().trim() : outchqEntity.getOutchqOrigsys());
		outchqEntity.setOutchqPartycode(StringUtils.isNotBlank(outchqRequestBean.getPartycode()) ? outchqRequestBean.getPartycode().trim() : outchqEntity.getOutchqPartycode());
		outchqEntity.setOutchqPartytype(StringUtils.isNotBlank(outchqRequestBean.getPartytype()) ? outchqRequestBean.getPartytype().trim() : outchqEntity.getOutchqPartytype());
		outchqEntity.setOutchqPaydate(Objects.nonNull(outchqRequestBean.getPaydate()) ? LocalDate.parse(outchqRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : outchqEntity.getOutchqPaydate());
		outchqEntity.setOutchqPaymode(StringUtils.isNotBlank(outchqRequestBean.getPaymode()) ? outchqRequestBean.getPaymode().trim() : outchqEntity.getOutchqPaymode());
		outchqEntity.setOutchqPayref(StringUtils.isNotBlank(outchqRequestBean.getPayref()) ? outchqRequestBean.getPayref().trim() : outchqEntity.getOutchqPayref());
		outchqEntity.setOutchqPrinted(Objects.nonNull(outchqRequestBean.getPrinted()) ? outchqRequestBean.getPrinted() : outchqEntity.getOutchqPrinted());
		outchqEntity.setOutchqPrintedon(Objects.nonNull(outchqRequestBean.getPrintedon()) ? LocalDate.parse(outchqRequestBean.getPrintedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : outchqEntity.getOutchqPrintedon());
		outchqEntity.setOutchqRecondate(Objects.nonNull(outchqRequestBean.getRecondate()) ? LocalDate.parse(outchqRequestBean.getRecondate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : outchqEntity.getOutchqRecondate());
		outchqEntity.setOutchqReconstmt(StringUtils.isNotBlank(outchqRequestBean.getReconstmt()) ? outchqRequestBean.getReconstmt().trim() : outchqEntity.getOutchqReconstmt());
		outchqEntity.setOutchqSite(StringUtils.isNotBlank(outchqRequestBean.getSite()) ? outchqRequestBean.getSite().trim() : outchqEntity.getOutchqSite());
		outchqEntity.setOutchqToday(LocalDateTime.now());
		outchqEntity.setOutchqUserid(StringUtils.isNotBlank(outchqRequestBean.getUserid()) ? outchqRequestBean.getUserid().trim() : outchqEntity.getOutchqUserid());


		return outchqEntity;
	};
}

