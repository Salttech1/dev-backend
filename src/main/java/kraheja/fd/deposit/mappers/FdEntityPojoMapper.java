package kraheja.fd.deposit.mappers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import kraheja.commons.bean.response.InchqResponseBean;
import kraheja.commons.entity.Inchq;
import kraheja.commons.utils.CommonConstraints;
import kraheja.fd.deposit.bean.response.DepintResponseBean;
import kraheja.fd.deposit.bean.response.DepintResponseBean.DepintResponseBeanBuilder;
import kraheja.fd.deposit.bean.response.DepositResponseBean;
import kraheja.fd.deposit.bean.response.DepositResponseBean.DepositResponseBeanBuilder;
import kraheja.fd.deposit.bean.response.Form15hgResponseBean;
import kraheja.fd.deposit.bean.response.Form15hgResponseBean.Form15hgResponseBeanBuilder;
import kraheja.fd.deposit.entity.Depint;
import kraheja.fd.deposit.entity.Deposit;
import kraheja.fd.deposit.entity.Form15hg;

public interface FdEntityPojoMapper {

	@SuppressWarnings("unchecked")
	public static Function<Object[], List<DepositResponseBean>> fetchDepositEntityPojoMapper = objectArray -> {
		DepositResponseBeanBuilder depositBeanBuilder = DepositResponseBean.builder();
		List<Deposit> depositEntityList  = (List<Deposit>)(Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		List<Inchq> inchqEntities  = (List<Inchq>)(Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String depositorName = (String)(Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);
		List<InchqResponseBean> inchqResponses = new ArrayList<>();
		return depositEntityList.stream().map(depositEntity -> {
			depositBeanBuilder
			.receiptnum(depositEntity.getDepositCK().getDepReceiptnum())
			.accint(depositEntity.getDepAccint())
			.bankcode(depositEntity.getDepBankcode())
			.brokcheq(depositEntity.getDepBrokcheq())
			.brokcheqdate(depositEntity.getDepBrokcheqdate())
			.broker(depositEntity.getDepBroker())
			.brokerage(depositEntity.getDepBrokerage())
			.brokper(depositEntity.getDepBrokper())
			.broktds(depositEntity.getDepBroktds())
			.canceldate(depositEntity.getDepCanceldate())
			.coy(depositEntity.getDepositCK().getDepCoy())
			.depamount(depositEntity.getDepDepamount())
			.instfdr(depositEntity.getDepInstFdr())
			.depdate(depositEntity.getDepDepdate())
			.depmonths(depositEntity.getDepDepmonths())
			.deporigin(depositEntity.getDepDeporigin())
			.depositor(depositEntity.getDepositCK().getDepDepositor())
			.disdate(depositEntity.getDepDisdate())
			.expirystatus(depositEntity.getDepExpiryStatus())
			.extendedyn(depositEntity.getDepExtendedyn())
			.grossint(depositEntity.getDepGrossint())
			.instbp(depositEntity.getDepInstBp())
			.instip(depositEntity.getDepInstIp())
			.instructions(depositEntity.getDepInstructions())
			.intaccyymm(depositEntity.getDepIntaccyymm())
			.intfreq(depositEntity.getDepIntfreq())
			.intpaidytd(depositEntity.getDepIntpaidytd())
			.intrate(depositEntity.getDepIntrate())
			.intrate2(depositEntity.getDepIntrate2())
			.intrateold(depositEntity.getDepIntrateold())
			.jowner1(depositEntity.getDepJowner1())
			.jowner2(depositEntity.getDepJowner2())
			.jowner3(depositEntity.getDepJowner3())
			.matamount(depositEntity.getDepMatamount())
			.liqtype(depositEntity.getDepLiqtype())
			.matdate(depositEntity.getDepMatdate())
			.ngid1(depositEntity.getDepNgid1())
			.ngid2(depositEntity.getDepNgid2())
			.ngid3(depositEntity.getDepNgid3())
			.ngname1(depositEntity.getDepNgname1())
			.ngname2(depositEntity.getDepNgname2())
			.ngname3(depositEntity.getDepNgname3())
			.nominee(depositEntity.getDepNominee())
			.origrate(depositEntity.getDepOrigrate())
			.origreceipt(depositEntity.getDepOrigreceipt())
			.origsite(depositEntity.getDepOrigsite())
			.payamount(depositEntity.getDepPayamount())
			.paydate(depositEntity.getDepPaydate())
			.payeecode(depositEntity.getDepPayeecode())
			.payref(depositEntity.getDepPayref())
			.prevmaturitydate(depositEntity.getDepPrevmaturitydate())
			.printedon(depositEntity.getDepPrintedon())
			.printrev(depositEntity.getDepPrintrev())
			.proprietor(depositEntity.getDepProprietor())
			.receiptdate(depositEntity.getDepReceiptdate())
			.site(depositEntity.getDepSite())
			.staffyn(depositEntity.getDepStaffyn())
			.taxpaidytd(depositEntity.getDepTaxpaidytd())
			.tds(depositEntity.getDepTds())
			.transfer(depositEntity.getDepTransfer())
			.waracc(depositEntity.getDepWarAcc())
			.warbank(depositEntity.getDepWarBank())
			.ddCharges(BigInteger.ZERO.intValue())
			.interest(BigInteger.ZERO.intValue())
			.isChecked(Boolean.FALSE)
			.depositorName(depositorName);
			if(CollectionUtils.isNotEmpty(inchqEntities)) {
				for(Inchq inchq: inchqEntities) {
					inchqResponses.add(FdEntityPojoMapper.fetchInchqEntityPojoMapper.apply(inchq));
				}	
				depositBeanBuilder.inchqResponseBean(inchqResponses);
			}
			return depositBeanBuilder.build();
		}).collect(Collectors.toList());
		//		return depositBeanBuilder.build();
	};

	public static Function<Inchq, InchqResponseBean> fetchInchqEntityPojoMapper = (inchqEntity) -> {
		InchqResponseBean.InchqResponseBeanBuilder inchqBuiler = InchqResponseBean.builder();

		inchqBuiler
		.num(inchqEntity.getInchqCk().getInchqNum())
		//.chqkNum(inchqEntity.getInchqCk().getInchqNum())
		.bank(inchqEntity.getInchqCk().getInchqBank())
		.actype(inchqEntity.getInchqActype())
		.amount(inchqEntity.getInchqAmount())
		.bouncedate(inchqEntity.getInchqBouncedate())
		.bounrevon(inchqEntity.getInchqBounrevon())
		.bounrevyn(inchqEntity.getInchqBounrevyn())
		.canceledate(inchqEntity.getInchqCanceledate())
		.coyBank(inchqEntity.getInchqCoybank())
		.date(inchqEntity.getInchqDate())
		.fund(inchqEntity.getInchqFund())
		.loanyn(inchqEntity.getInchqLoanyn())
		.origsite(inchqEntity.getInchqOrigsite())
		.origsys(inchqEntity.getInchqOrigsys())
		.outstat(inchqEntity.getInchqOutstat())
		.partycode(inchqEntity.getInchqPartycode())
		.paymode(inchqEntity.getInchqPaymode())
		.proprietor(inchqEntity.getInchqProprietor())
		.recondate(inchqEntity.getInchqRecondate())
		.reconstmt(inchqEntity.getInchqReconstmt())
		.remark(inchqEntity.getInchqRemark())
		.resubcount(inchqEntity.getInchqResubcount())
		.resubdate(inchqEntity.getInchqResubdate())
		.site(inchqEntity.getInchqSite())
		.slipnum(inchqEntity.getInchqSlipnum())
		.today(inchqEntity.getInchqToday())
		.userid(inchqEntity.getInchqUserid());
		return inchqBuiler.build();
	};

	@SuppressWarnings("unchecked")
	public static Function<Object[], List<DepintResponseBean>> fetchDepintEntityPojoMapper = objectArray -> {
		DepintResponseBeanBuilder depintBeanBuilder = DepintResponseBean.builder();
		List<Depint> depintEntityList = (List<Depint>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		Map<String, String> depositorNameMap =  objectArray.length > BigInteger.ONE.intValue() ? (Map<String, String>) objectArray[BigInteger.ONE.intValue()] : null;
		return depintEntityList.stream().map(depintEntity -> {
			depintBeanBuilder.depositor(depintEntity.getDepintCK().getDinDepositor())
			.receiptnum(depintEntity.getDepintCK().getDinReceiptnum())
			.depositorName(org.apache.commons.collections4.MapUtils.isNotEmpty(depositorNameMap) && depositorNameMap.containsKey(depintEntity.getDepintCK().getDinDepositor())? depositorNameMap.get(depintEntity.getDepintCK().getDinDepositor()): null)
			.intfrom(Objects.nonNull(depintEntity.getDepintCK().getDinIntfrom()) ? depintEntity.getDepintCK().getDinIntfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.intupto(Objects.nonNull(depintEntity.getDepintCK().getDinIntupto()) ? depintEntity.getDepintCK().getDinIntupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.coy(depintEntity.getDepintCK().getDinCoy())
			.chqnum(depintEntity.getDepintCK().getDinChqnum())
			.bankcode(depintEntity.getDinBankcode())
			.canceldate(Objects.nonNull(depintEntity.getDinCanceldate()) ? depintEntity.getDinCanceldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.interest(depintEntity.getDinInterest())
			.newprin(depintEntity.getDinNewprin())
			.origsite(depintEntity.getDinOrigsite())
			.proprietor(depintEntity.getDinProprietor())
			.site(depintEntity.getDinSite())
			.staffallow(depintEntity.getDinStaffallow())
			.taxalwaysyn(depintEntity.getDinTaxalwaysyn())
			.tds(depintEntity.getDinTds())
			.tds15hyn(depintEntity.getDinTds15hyn())
			.today(Objects.nonNull(depintEntity.getDinToday()) ? depintEntity.getDinToday().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.transer(depintEntity.getDinTranser())
			.userid(depintEntity.getDinUserid())
			;
			return depintBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	@SuppressWarnings("unchecked")
	public static Function<Object[], List<Form15hgResponseBean>> fetchForm15hgEntityPojoMapper = objectArray -> {
		Form15hgResponseBeanBuilder form15hgBeanBuilder = Form15hgResponseBean.builder();
		List<Form15hg> form15hgEntityList = (List<Form15hg>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		return form15hgEntityList.stream().map(form15hgEntity -> {
			form15hgBeanBuilder.acyear(form15hgEntity.getForm15hgCK().getFormAcyear())
			.coy(form15hgEntity.getForm15hgCK().getFormCoy())
			.depositor(form15hgEntity.getForm15hgCK().getFormDepositor())
			.quarter(form15hgEntity.getForm15hgCK().getFormQuarter())
			.amtincomepaid(form15hgEntity.getFormAmtincomepaid())
			.assessedyn(form15hgEntity.getFormAssessedyn())
			.assessyear(form15hgEntity.getFormAssessyear())
			.datedeclReceived(Objects.nonNull(form15hgEntity.getFormDatedeclreceived()) ? form15hgEntity.getFormDatedeclreceived().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.dateIncomePaid(Objects.nonNull(form15hgEntity.getFormDateincomepaid()) ?   Timestamp.valueOf(form15hgEntity.getFormDateincomepaid()) : null)
			.estimatedIncome(form15hgEntity.getFormEstimatedincome())
			.estimatedtotIncome(form15hgEntity.getFormEstimatedtotincome())
			.origsite(form15hgEntity.getFormOrigsite())
			.site(form15hgEntity.getFormSite())
			.today(Objects.nonNull(form15hgEntity.getFormToday()) ? form15hgEntity.getFormToday().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.totalFormFiled(form15hgEntity.getFormTotalformfiled())
			.uniqueid(form15hgEntity.getFormUniqueid())
			.userid(form15hgEntity.getFormUserid())
			.incomeFormFiled(form15hgEntity.getFormIncomeformfiled())
			;
			return form15hgBeanBuilder.build();
		}).collect(Collectors.toList());
	};
}