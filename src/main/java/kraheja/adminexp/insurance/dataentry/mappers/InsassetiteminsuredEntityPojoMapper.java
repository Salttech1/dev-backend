package kraheja.adminexp.insurance.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.adminexp.insurance.dataentry.bean.request.InsassetiteminsuredRequestBean;
import kraheja.adminexp.insurance.dataentry.bean.response.InsassetiteminsuredResponseBean;
import kraheja.adminexp.insurance.dataentry.bean.response.InsassetiteminsuredResponseBean.InsassetiteminsuredResponseBeanBuilder;
import kraheja.adminexp.insurance.dataentry.entity.Insassetiteminsured;
import kraheja.adminexp.insurance.dataentry.entity.InsassetiteminsuredCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;

public interface InsassetiteminsuredEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<InsassetiteminsuredResponseBean>> fetchInsassetiteminsuredEntityPojoMapper = objectArray -> {
		InsassetiteminsuredResponseBeanBuilder insassetiteminsuredBeanBuilder = InsassetiteminsuredResponseBean.builder();
		List<Insassetiteminsured> insassetiteminsuredEntityList = (List<Insassetiteminsured>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return insassetiteminsuredEntityList.stream().map(insassetiteminsuredEntity -> {
			insassetiteminsuredBeanBuilder.policyid(insassetiteminsuredEntity.getInsassetiteminsuredCK().getIaiPolicyid())
					.linenumber(insassetiteminsuredEntity.getInsassetiteminsuredCK().getIaiLinenumber())
					.brand(insassetiteminsuredEntity.getIaiBrand())
					.epcgvalue(insassetiteminsuredEntity.getIaiEpcgvalue())
					.groupcode(insassetiteminsuredEntity.getIaiGroupcode())
					.itemserial(insassetiteminsuredEntity.getIaiitemserial())
					.srno(insassetiteminsuredEntity.getIaisrno())
					.groupname(insassetiteminsuredEntity.getIaiGroupname())
					.ipaddress(insassetiteminsuredEntity.getIaiIpaddress())
					.item(insassetiteminsuredEntity.getIaiItem())
					.itemdesc(insassetiteminsuredEntity.getIaiItemdesc())
					.machinename(insassetiteminsuredEntity.getIaiMachinename())
					.modifiedon(insassetiteminsuredEntity.getIaiModifiedon())
					.module(insassetiteminsuredEntity.getIaiModule())
					.purchasedate(Objects.nonNull(insassetiteminsuredEntity.getIaiPurchasedate()) ? insassetiteminsuredEntity.getIaiPurchasedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.purchaseyear(insassetiteminsuredEntity.getIaiPurchaseyear())
					.qty(insassetiteminsuredEntity.getIaiQty())
					.qtydesc(insassetiteminsuredEntity.getIaiQtydesc())
					.remark(insassetiteminsuredEntity.getIaiRemark())
					.revisedvalue(insassetiteminsuredEntity.getIaiRevisedvalue())
					.site(insassetiteminsuredEntity.getIaiSite())
					.subgroupcode(insassetiteminsuredEntity.getIaiSubgroupcode())
					.subgroupname(insassetiteminsuredEntity.getIaiSubgroupname())
					.userid(insassetiteminsuredEntity.getIaiUserid())
					.value(insassetiteminsuredEntity.getIaiValue())
					.valuewithduty(insassetiteminsuredEntity.getIaiValuewithduty())
;
			return insassetiteminsuredBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], List<Insassetiteminsured>> addInsassetiteminsuredEntityPojoMapper = objectArray -> {
		List<InsassetiteminsuredRequestBean> insassetiteminsuredRequestBeanList = (List<InsassetiteminsuredRequestBean>) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String policyId = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);

		return insassetiteminsuredRequestBeanList.stream().map(
				insassetiteminsuredRequestBean -> { 
			return Insassetiteminsured.builder()
				.insassetiteminsuredCK(InsassetiteminsuredCK.builder()
						.iaiPolicyid(policyId)
						.iaiLinenumber(insassetiteminsuredRequestBean.getLinenumber())
			.build())
						.iaiPolicynumber(insassetiteminsuredRequestBean.getPolicynumber())
						.iaisrno(insassetiteminsuredRequestBean.getSrno())
						.iaiBldgcode(insassetiteminsuredRequestBean.getBldgcode())
						.iaiGroupcode(insassetiteminsuredRequestBean.getGroupcode())
						.iaiBrand(insassetiteminsuredRequestBean.getBrand())
						.iaiEpcgvalue(insassetiteminsuredRequestBean.getEpcgvalue())
						.iaiGroupname(insassetiteminsuredRequestBean.getGroupname())
						.iaiIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
						.iaiItem(insassetiteminsuredRequestBean.getItem())
						.iaiItemdesc(insassetiteminsuredRequestBean.getItemdesc())
						.iaiitemserial(insassetiteminsuredRequestBean.getItemserial())
						.iaiMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
						.iaiModifiedon(LocalDateTime.now())
						.iaiModule(insassetiteminsuredRequestBean.getModule())
						.iaiPurchasedate(Objects.nonNull(insassetiteminsuredRequestBean.getPurchasedate()) ? LocalDate.parse(insassetiteminsuredRequestBean.getPurchasedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
						.iaiPurchaseyear(insassetiteminsuredRequestBean.getPurchaseyear())
						.iaiQty(insassetiteminsuredRequestBean.getQty())
						.iaiQtydesc(insassetiteminsuredRequestBean.getQtydesc())
						.iaiRemark(insassetiteminsuredRequestBean.getRemark())
						.iaiRevisedvalue(insassetiteminsuredRequestBean.getRevisedvalue())
						.iaiSite(GenericAuditContextHolder.getContext().getSite())
						.iaiSubgroupcode(insassetiteminsuredRequestBean.getSubgroupcode())
						.iaiSubgroupname(insassetiteminsuredRequestBean.getSubgroupname())
						.iaiUserid(GenericAuditContextHolder.getContext().getUserid())
						.iaiValue(insassetiteminsuredRequestBean.getValue())
						.iaiValuewithduty(insassetiteminsuredRequestBean.getValuewithduty()).build();
				}).collect(Collectors.toList());
};

	public static BiFunction<Insassetiteminsured, InsassetiteminsuredRequestBean, Insassetiteminsured> updateInsassetiteminsuredEntityPojoMapper = (insassetiteminsuredEntity, insassetiteminsuredRequestBean) -> {
		insassetiteminsuredEntity.getInsassetiteminsuredCK().setIaiPolicyid(Objects.nonNull(insassetiteminsuredRequestBean.getPolicyid()) ? insassetiteminsuredRequestBean.getPolicyid().trim() : insassetiteminsuredEntity.getInsassetiteminsuredCK().getIaiPolicyid());
		insassetiteminsuredEntity.getInsassetiteminsuredCK().setIaiLinenumber(Objects.nonNull(insassetiteminsuredRequestBean.getLinenumber()) ? insassetiteminsuredRequestBean.getLinenumber() : insassetiteminsuredEntity.getInsassetiteminsuredCK().getIaiLinenumber());
		insassetiteminsuredEntity.setIaiBrand(Objects.nonNull(insassetiteminsuredRequestBean.getBrand()) ? insassetiteminsuredRequestBean.getBrand().trim() : insassetiteminsuredEntity.getIaiBrand());
		insassetiteminsuredEntity.setIaisrno(Objects.nonNull(insassetiteminsuredRequestBean.getSrno()) ? insassetiteminsuredRequestBean.getSrno().trim() : insassetiteminsuredEntity.getIaisrno());
		insassetiteminsuredEntity.setIaiBldgcode(Objects.nonNull(insassetiteminsuredRequestBean.getBldgcode()) ? insassetiteminsuredRequestBean.getBldgcode().trim() : insassetiteminsuredEntity.getIaiBldgcode());
		insassetiteminsuredEntity.setIaiGroupcode(Objects.nonNull(insassetiteminsuredRequestBean.getGroupcode()) ? insassetiteminsuredRequestBean.getGroupcode().trim() : insassetiteminsuredEntity.getIaiGroupcode());
		insassetiteminsuredEntity.setIaiEpcgvalue(Objects.nonNull(insassetiteminsuredRequestBean.getEpcgvalue()) ? insassetiteminsuredRequestBean.getEpcgvalue() : insassetiteminsuredEntity.getIaiEpcgvalue());
		insassetiteminsuredEntity.setIaiGroupname(Objects.nonNull(insassetiteminsuredRequestBean.getGroupname()) ? insassetiteminsuredRequestBean.getGroupname().trim() : insassetiteminsuredEntity.getIaiGroupname());
		insassetiteminsuredEntity.setIaiIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress());
		insassetiteminsuredEntity.setIaiPolicynumber(Objects.nonNull(insassetiteminsuredRequestBean.getPolicynumber()) ? insassetiteminsuredRequestBean.getPolicynumber().trim() : insassetiteminsuredEntity.getIaiPolicynumber());
		insassetiteminsuredEntity.setIaiitemserial(Objects.nonNull(insassetiteminsuredRequestBean.getItemserial()) ? insassetiteminsuredRequestBean.getItemserial().trim() : insassetiteminsuredEntity.getIaiitemserial());
		insassetiteminsuredEntity.setIaiItem(Objects.nonNull(insassetiteminsuredRequestBean.getItem()) ? insassetiteminsuredRequestBean.getItem().trim() : insassetiteminsuredEntity.getIaiItem());
		insassetiteminsuredEntity.setIaiItemdesc(Objects.nonNull(insassetiteminsuredRequestBean.getItemdesc()) ? insassetiteminsuredRequestBean.getItemdesc().trim() : insassetiteminsuredEntity.getIaiItemdesc());
		insassetiteminsuredEntity.setIaiMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName());
		insassetiteminsuredEntity.setIaiModifiedon(Objects.nonNull(insassetiteminsuredRequestBean.getModifiedon()) ? insassetiteminsuredRequestBean.getModifiedon() : insassetiteminsuredEntity.getIaiModifiedon());
		insassetiteminsuredEntity.setIaiModule(Objects.nonNull(insassetiteminsuredRequestBean.getModule()) ? insassetiteminsuredRequestBean.getModule().trim() : insassetiteminsuredEntity.getIaiModule());
		insassetiteminsuredEntity.setIaiPurchasedate(Objects.nonNull(insassetiteminsuredRequestBean.getPurchasedate()) ? LocalDate.parse(insassetiteminsuredRequestBean.getPurchasedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : insassetiteminsuredEntity.getIaiPurchasedate());
		insassetiteminsuredEntity.setIaiPurchaseyear(Objects.nonNull(insassetiteminsuredRequestBean.getPurchaseyear()) ? insassetiteminsuredRequestBean.getPurchaseyear().trim() : insassetiteminsuredEntity.getIaiPurchaseyear());
		insassetiteminsuredEntity.setIaiQty(Objects.nonNull(insassetiteminsuredRequestBean.getQty()) ? insassetiteminsuredRequestBean.getQty() : insassetiteminsuredEntity.getIaiQty());
		insassetiteminsuredEntity.setIaiQtydesc(Objects.nonNull(insassetiteminsuredRequestBean.getQtydesc()) ? insassetiteminsuredRequestBean.getQtydesc().trim() : insassetiteminsuredEntity.getIaiQtydesc());
		insassetiteminsuredEntity.setIaiRemark(Objects.nonNull(insassetiteminsuredRequestBean.getRemark()) ? insassetiteminsuredRequestBean.getRemark().trim() : insassetiteminsuredEntity.getIaiRemark());
		insassetiteminsuredEntity.setIaiRevisedvalue(Objects.nonNull(insassetiteminsuredRequestBean.getRevisedvalue()) ? insassetiteminsuredRequestBean.getRevisedvalue() : insassetiteminsuredEntity.getIaiRevisedvalue());
		insassetiteminsuredEntity.setIaiSite(Objects.nonNull(insassetiteminsuredRequestBean.getSite()) ? insassetiteminsuredRequestBean.getSite().trim() : insassetiteminsuredEntity.getIaiSite());
		insassetiteminsuredEntity.setIaiSubgroupcode(Objects.nonNull(insassetiteminsuredRequestBean.getSubgroupcode()) ? insassetiteminsuredRequestBean.getSubgroupcode().trim() : insassetiteminsuredEntity.getIaiSubgroupcode());
		insassetiteminsuredEntity.setIaiSubgroupname(Objects.nonNull(insassetiteminsuredRequestBean.getSubgroupname()) ? insassetiteminsuredRequestBean.getSubgroupname().trim() : insassetiteminsuredEntity.getIaiSubgroupname());
		insassetiteminsuredEntity.setIaiUserid(Objects.nonNull(insassetiteminsuredRequestBean.getUserid()) ? insassetiteminsuredRequestBean.getUserid().trim() : insassetiteminsuredEntity.getIaiUserid());
		insassetiteminsuredEntity.setIaiValue(Objects.nonNull(insassetiteminsuredRequestBean.getValue()) ? insassetiteminsuredRequestBean.getValue() : insassetiteminsuredEntity.getIaiValue());
		insassetiteminsuredEntity.setIaiValuewithduty(Objects.nonNull(insassetiteminsuredRequestBean.getValuewithduty()) ? insassetiteminsuredRequestBean.getValuewithduty() : insassetiteminsuredEntity.getIaiValuewithduty());


		return insassetiteminsuredEntity;
	};

}
